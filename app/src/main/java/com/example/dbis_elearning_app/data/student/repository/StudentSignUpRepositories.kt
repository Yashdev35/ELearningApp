package com.example.dbis_elearning_app.data.student.repository

import com.example.dbis_elearning_app.data.model.Student
import com.example.dbis_elearning_app.data.student.model.ApiResponse
import com.example.dbis_elearning_app.data.student.model.AuthResponse
import retrofit2.Response

interface StuSignUpRepository {
    suspend fun signUpUser(name: String, email: String, password: String): ResultCus<AuthResponse>
    suspend fun signUpUserWith(accessToken: String): Response<ApiResponse<Student>>
}
