package com.example.dbis_elearning_app.data.student.repository

import android.util.Log
import com.example.dbis_elearning_app.Api.APIStu.StudentApi
import com.example.dbis_elearning_app.auth.AuthManager
import com.example.dbis_elearning_app.data.student.model.AuthResponse
import com.example.dbis_elearning_app.data.student.model.DashboardData
import com.example.dbis_elearning_app.data.student.model.EducationLevel
import com.example.dbis_elearning_app.data.student.model.PostgraduateRequest
import com.example.dbis_elearning_app.data.student.model.SchoolRequest
import com.example.dbis_elearning_app.data.student.model.UndergraduateRequest
import com.example.dbis_elearning_app.data.student.model.UserData
import com.example.dbis_elearning_app.data.student.model.UserSignUpRequest
import retrofit2.Response
import javax.inject.Inject

class StudentDataRepoImpl @Inject constructor(
    private val userApi: StudentApi,
    private val authManager: AuthManager
) : StuDataRepo {
    override suspend fun signUpUser(
        name: String,
        email: String,
        password: String
    ): ResultCus<AuthResponse> {
        return try {
            val request = UserSignUpRequest(name, email, password)
            val response = userApi.signUpUser(request)
            handleAuthResponse(response)
        } catch (e: Exception) {
            ResultCus.Error(e)
        }
    }
    override suspend fun getDashboard(): ResultCus<DashboardData> {
        return try {
            Log.d("Dashboard", "Fetching dashboard data")

            // Get the cookie token
            val cookieToken = authManager.getCookieToken()
            Log.d("Dashboard", "Cookie token: $cookieToken")

            // Format cookie header if token exists
            val cookieHeader = cookieToken?.let { "accessToken=$it" }

            val response = userApi.getDashboard(cookieHeader)

            if (response.isSuccessful) {
                response.body()?.let { apiResponse ->
                    ResultCus.Success(apiResponse.data!!)
                } ?: ResultCus.Error(Exception("Empty response body"))
            } else {
                // Check if it's an authentication error
                if (response.code() == 401) {
                    Log.e("Dashboard", "Authentication failed. Token might be expired")
                    // You might want to handle token refresh here
                    authManager.clearAll() // Clear invalid tokens
                }
                ResultCus.Error(Exception("Failed to get dashboard: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("Dashboard", "Error fetching dashboard", e)
            ResultCus.Error(e)
        }
    }

    // Helper function to refresh token if needed
    private suspend fun refreshToken(): Boolean {
        // Implement token refresh logic here if needed
        return false
    }

    override suspend fun signUpWithGoogle(token: String): ResultCus<AuthResponse> {
        return try {
            val response = userApi.signUpWithGoogle("Bearer $token")
            handleAuthResponse(response)
        } catch (e: Exception) {
            ResultCus.Error(e)
        }
    }

    override suspend fun updateEducation(educationLevel: EducationLevel): ResultCus<Unit> {
        return try {
            Log.d("EducationUpdate", "StartedRepoImpl")
            val response = when (educationLevel.level1.uppercase()) {
                "SCHOOL" -> userApi.updateSchoolEducation(
                    SchoolRequest(
                        schoolingYear = educationLevel.level2,
                        schoolStream = educationLevel.level3
                    )
                )
                "UNDERGRADUATE" -> userApi.updateUndergraduateEducation(
                    UndergraduateRequest(
                        studyYear = educationLevel.level2,
                        degree = educationLevel.level3
                    )
                )
                "POSTGRADUATE" -> {
                    educationLevel.level4?.let { specialization ->
                        userApi.updatePostgraduateEducation(
                            PostgraduateRequest(
                                studyYear = educationLevel.level2,
                                degree = educationLevel.level3,
                                specialization = specialization
                            )
                        )
                    } ?: throw IllegalArgumentException("Specialization is required for postgraduate education")
                }
                else -> throw IllegalArgumentException("Invalid education level: ${educationLevel.level1}")
            }
            Log.d("EducationUpdate", "EndedRepoImpl $response")
            if (response.isSuccessful) {
                ResultCus.Success(Unit)
            } else {
                ResultCus.Error(Exception("Failed to update education: ${response.code()}"))
            }
        } catch (e: Exception) {
            ResultCus.Error(e)
        }
    }

    override suspend fun getUserProfile(): ResultCus<UserData> {
//        return try {
//            val response = userApi.getUserProfile()
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    Result.Success(it)
//                } ?: Result.Error(Exception("Empty response body"))
//            } else {
//                Result.Error(Exception("Failed to get profile: ${response.code()}"))
//            }
//        } catch (e: Exception) {
//            Result.Error(e)
//        }
        return ResultCus.Error(Exception("Not implemented"))
    }

    override fun logout() {
        authManager.clearAll()
    }

    private fun handleAuthResponse(response: Response<AuthResponse>): ResultCus<AuthResponse> {
        return if (response.isSuccessful) {
            response.body()?.let { authResponse ->
                // Extract tokens from the message
                val accessToken = authResponse.message.accessToken
                val refreshToken = authResponse.message.refreshToken

                // Create UserData object
                val userData = UserData(
                    id = authResponse.data.id.toString(),
                    name = authResponse.data.name ?: "",
                    username = authResponse.data.username,
                    email = authResponse.data.email,
                    profilePicture = authResponse.data.profilepicture,
                    createdAt = authResponse.data.createdAt,
                    updatedAt = authResponse.data.updatedAt,
                    accessToken = accessToken,
                    refreshToken = refreshToken
                )

                // Save user data and tokens
                authManager.apply {
                    saveAccessToken(accessToken)
                    saveRefreshToken(refreshToken)
                    saveCookieToken(accessToken)  // This will be picked up by the CookieJar
                    saveUserData(userData)
                }

                ResultCus.Success(authResponse)
            } ?: ResultCus.Error(Exception("Empty response body"))
        } else {
            ResultCus.Error(Exception("Authentication failed: ${response.code()}"))
        }
    }
}
sealed class ResultCus<T> {
    class Initial<T> : ResultCus<T>()
    class Loading<T> : ResultCus<T>()
    data class Success<T>(val data: T) : ResultCus<T>()
    data class Error<T>(val exception: Throwable) : ResultCus<T>()
}