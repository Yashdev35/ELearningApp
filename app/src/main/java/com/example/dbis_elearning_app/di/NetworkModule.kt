package com.example.dbis_elearning_app.di

import com.example.dbis_elearning_app.Api.APIIns.InstructorApi
import com.example.dbis_elearning_app.Api.APIStu.StudentApi
import com.example.dbis_elearning_app.data.student.repository.StuSignUpRepository
import com.example.dbis_elearning_app.data.student.repository.StuSignUpRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://10.18.3.102:5001/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

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
    fun provideUserRepository(apiService: StudentApi): StuSignUpRepository {
        return StuSignUpRepositoryImpl(apiService)
    }
}