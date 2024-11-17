package com.example.dbis_elearning_app.data.student.repository

import com.example.dbis_elearning_app.data.model.Student
import com.example.dbis_elearning_app.data.student.model.ApiResponse
import com.example.dbis_elearning_app.data.student.model.UserSignUpRequest

interface StuSignUpRepository {
    suspend fun signUpUser(userSignUpRequest: UserSignUpRequest): ApiResponse<Student>
    suspend fun signUpUserWith(accessToken: String): ApiResponse<Student>
}