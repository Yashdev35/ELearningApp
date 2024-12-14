package com.example.dbis_elearning_app.Api.APIStu

import androidx.room.Upsert
import com.example.dbis_elearning_app.data.model.Student
import com.example.dbis_elearning_app.data.student.model.ApiResponse
import com.example.dbis_elearning_app.data.student.model.ApiResponseDash
import com.example.dbis_elearning_app.data.student.model.AuthResponse
import com.example.dbis_elearning_app.data.student.model.DashboardData
import com.example.dbis_elearning_app.data.student.model.DynamicEducationRequest
import com.example.dbis_elearning_app.data.student.model.PostgraduateRequest
import com.example.dbis_elearning_app.data.student.model.SchoolRequest
import com.example.dbis_elearning_app.data.student.model.StudentData
import com.example.dbis_elearning_app.data.student.model.UndergraduateRequest
import com.example.dbis_elearning_app.data.student.model.UserData
import com.example.dbis_elearning_app.data.student.model.UserSignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StudentApi {
    @POST("api/v1/user/app/signup")
    suspend fun signUpUser(
        @Body request: UserSignUpRequest,
        @Header("Existing-Access-Token") existingAccessToken: String? = null,
        @Header("Existing-Refresh-Token") existingRefreshToken: String? = null
    ): Response<AuthResponse>

    @POST("api/v1/user/app/signup")
    suspend fun signUpWithGoogle(
        @Header("Authorization") bearerToken: String
    ): Response<AuthResponse>

    @POST("api/v1/user/app/signup")
    suspend fun signUpUserWith(@Body accessToken: String): Response<ApiResponse<Student>>
    @PUT("api/v1/user/app/education")
    suspend fun updateSchoolEducation(@Body request: SchoolRequest): Response<Unit>

    @PUT("api/v1/user/app/education")
    suspend fun updateUndergraduateEducation(@Body request: UndergraduateRequest): Response<Unit>

    @PUT("api/v1/user/app/education")
    suspend fun updatePostgraduateEducation(@Body request: PostgraduateRequest): Response<Unit>

    @GET("api/v1/user/app/dashboard")
    suspend fun getDashboard(
        @Header("Authorization") cookie: String? = null
    ): Response<ApiResponse<DashboardData>>
}

