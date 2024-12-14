package com.example.dbis_elearning_app.di

import android.content.Context
import android.util.Log
import com.example.dbis_elearning_app.Api.APIIns.InstructorApi
import com.example.dbis_elearning_app.Api.APIStu.StudentApi
import com.example.dbis_elearning_app.auth.AuthManager
import com.example.dbis_elearning_app.data.student.repository.StuSignUpRepository
import com.example.dbis_elearning_app.data.student.repository.StuSignUpRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://10.0.2.2:5001/"

    @Provides
    @Singleton
    fun provideInstructorApi(retrofit: Retrofit): InstructorApi {
        return retrofit.create(InstructorApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStudentApi(retrofit: Retrofit): StudentApi {
        return retrofit.create(StudentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        apiService: StudentApi,
        authManager: AuthManager): StuSignUpRepository {
        return StuSignUpRepositoryImpl(apiService,authManager)
    }
    @Provides
    @Singleton
    fun provideAuthManager(@ApplicationContext context: Context): AuthManager {
        return AuthManager(context)
    }

    @Provides
    @Singleton
    fun provideCookieJar(authManager: AuthManager): CookieJar {
        return object : CookieJar {
            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cookies.forEach { cookie ->
                    when (cookie.name) {
                        "accessToken" -> {
                            authManager.saveCookieToken(cookie.value)
                            Log.d("CookieJar", "Saved accessToken cookie: ${cookie.value}")
                        }
                        "refreshToken" -> {
                            authManager.saveRefreshToken(cookie.value)
                            Log.d("CookieJar", "Saved refreshToken cookie: ${cookie.value}")
                        }
                    }
                }
            }

            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                val cookies = mutableListOf<Cookie>()

                // Add access token cookie if available
                authManager.getCookieToken()?.let { token ->
                    Log.d("CookieJar", "Loading accessToken cookie: $token")
                    cookies.add(
                        Cookie.Builder()
                            .name("accessToken")
                            .value(token)
                            .domain(url.host)
                            .path("/")
                            .secure()
                            .httpOnly()
                            .build()
                    )
                }

                // Add refresh token cookie if available
                authManager.getRefreshToken()?.let { token ->
                    Log.d("CookieJar", "Loading refreshToken cookie: $token")
                    cookies.add(
                        Cookie.Builder()
                            .name("refreshToken")
                            .value(token)
                            .domain(url.host)
                            .path("/")
                            .secure()
                            .httpOnly()
                            .build()
                    )
                }

                return cookies
            }
        }
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(authManager: AuthManager): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()

            // Add Bearer token if available
            authManager.getAccessToken()?.let { token ->
                Log.d("AuthInterceptor", "Adding Bearer token: $token")
                requestBuilder.header("Authorization", "Bearer $token")
            }

            // Add Cookie header if cookie token is available
            authManager.getCookieToken()?.let { token ->
                Log.d("AuthInterceptor", "Adding Cookie token: $token")
                requestBuilder.header("Cookie", "accessToken=$token")
            }

            chain.proceed(requestBuilder.build())
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: Interceptor,
        cookieJar: CookieJar
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val request = chain.request()
                Log.d("OkHttp", "Request headers: ${request.headers}")
                val response = chain.proceed(request)
                Log.d("OkHttp", "Response headers: ${response.headers}")
                response
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}