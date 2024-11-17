package com.example.dbis_elearning_app.data.student.repository

import com.example.dbis_elearning_app.data.model.Student
import com.example.dbis_elearning_app.data.student.model.ApiResponse
import com.example.dbis_elearning_app.data.student.model.UserSignUpRequest
import retrofit2.Response

interface StuSignUpRepository {
    suspend fun signUpUser(userSignUpRequest: UserSignUpRequest): Response<ApiResponse<Student>>
    suspend fun signUpUserWith(accessToken: String): Response<ApiResponse<Student>>
}
