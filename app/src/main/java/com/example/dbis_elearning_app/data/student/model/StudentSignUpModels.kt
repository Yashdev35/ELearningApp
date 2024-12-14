package com.example.dbis_elearning_app.data.student.model

import com.google.gson.annotations.SerializedName

// UserSignUpRequest.kt
data class UserSignUpRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

// ApiResponse.kt
data class ApiResponse<T>(
    val statusCode: Int,
    val data: T?,
    val message: String
)