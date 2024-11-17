package com.example.dbis_elearning_app.data.student.model

// UserSignUpRequest.kt
data class UserSignUpRequest(
    val name: String,
    val email: String,
    val password: String
)

// ApiResponse.kt
data class ApiResponse<T>(
    val statusCode: Int,
    val data: T?,
    val message: String
)