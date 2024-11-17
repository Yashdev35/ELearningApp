package com.example.dbis_elearning_app.data.student.repository

import com.example.dbis_elearning_app.Api.APIStu.StudentApi
import com.example.dbis_elearning_app.data.model.Student
import com.example.dbis_elearning_app.data.student.model.ApiResponse
import com.example.dbis_elearning_app.data.student.model.UserSignUpRequest
import retrofit2.Response
import javax.inject.Inject

class StuSignUpRepositoryImpl @Inject constructor(
    private val stuApiService: StudentApi
) : StuSignUpRepository {

    override suspend fun signUpUser(userSignUpRequest: UserSignUpRequest): Response<ApiResponse<Student>> {
        return stuApiService.signUpUser(userSignUpRequest)
    }

    override suspend fun signUpUserWith(accessToken: String): Response<ApiResponse<Student>> {
        return stuApiService.signUpUserWith(accessToken)
    }
}
