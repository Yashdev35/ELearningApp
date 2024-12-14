package com.example.dbis_elearning_app.data.student.repository

import com.example.dbis_elearning_app.Api.APIStu.StudentApi
import com.example.dbis_elearning_app.auth.AuthManager
import com.example.dbis_elearning_app.data.model.Student
import com.example.dbis_elearning_app.data.student.model.ApiResponse
import com.example.dbis_elearning_app.data.student.model.AuthResponse
import com.example.dbis_elearning_app.data.student.model.UserData
import com.example.dbis_elearning_app.data.student.model.UserSignUpRequest
import retrofit2.Response
import javax.inject.Inject

class StuSignUpRepositoryImpl @Inject constructor(
    private val stuApiService: StudentApi,
    private val authManager: AuthManager
) : StuSignUpRepository {
    override suspend fun signUpUser(
        name: String,
        email: String,
        password: String
    ): ResultCus<AuthResponse> {
        return try {
            // First try to get existing tokens
            val existingAccessToken = authManager.getAccessToken()
            val existingRefreshToken = authManager.getRefreshToken()

            val request = UserSignUpRequest(
                name = name,
                email = email,
                password = password
            )

            // Add existing tokens to the request headers if they exist
            val response = if (existingAccessToken != null && existingRefreshToken != null) {
                stuApiService.signUpUser(
                    request = request,
                    existingAccessToken = existingAccessToken,
                    existingRefreshToken = existingRefreshToken
                )
            } else {
                stuApiService.signUpUser(request)
            }

            handleAuthResponse(response)
        } catch (e: Exception) {
            ResultCus.Error(e)
        }
    }

    private fun handleAuthResponse(response: Response<AuthResponse>): ResultCus<AuthResponse> {
        return if (response.isSuccessful) {
            response.body()?.let { authResponse ->
                if (authResponse.success && authResponse.statusCode == 200) {
                    // Extract tokens from the message
                    val accessToken = authResponse.message.accessToken
                    val refreshToken = authResponse.message.refreshToken

                    // Create UserData object from the response
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

                    // Save everything in AuthManager
                    authManager.apply {
                        saveAccessToken(accessToken)
                        saveRefreshToken(refreshToken)
                        saveCookieToken(accessToken)
                        saveUserData(userData)
                    }

                    ResultCus.Success(authResponse)
                } else {
                    ResultCus.Error(Exception("Authentication failed: ${authResponse.statusCode}"))
                }
            } ?: ResultCus.Error(Exception("Empty response body"))
        } else {
            if (response.code() == 401) {
                // Clear existing tokens on 401 and let the user retry
                authManager.clearAll()
            }
            ResultCus.Error(Exception("Authentication failed: ${response.code()}"))
        }
    }

    override suspend fun signUpUserWith(accessToken: String): Response<ApiResponse<Student>> {
        return stuApiService.signUpUserWith(accessToken)
    }
}
