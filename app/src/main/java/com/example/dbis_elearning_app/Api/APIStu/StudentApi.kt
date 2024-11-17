package com.example.dbis_elearning_app.Api.APIStu

import androidx.room.Upsert
import com.example.dbis_elearning_app.data.model.Student
import com.example.dbis_elearning_app.data.student.model.ApiResponse
import com.example.dbis_elearning_app.data.student.model.UserSignUpRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StudentApi {
    @POST("api/v1/user/app/signup")
    suspend fun signUpUser(@Body userSignUpRequest: UserSignUpRequest): ApiResponse<Student>

    @POST("api/v1/user/app/signup")
    suspend fun signUpUserWith(@Body accessToken: String): ApiResponse<Student>

    @GET("api/v1/user/app/signup")
    suspend fun getUser(): ApiResponse<Student>

    @GET("api/v1/user/app/{id}")
    suspend fun getStudent(@Path("id") id: String): ApiResponse<Student>

    @PUT("api/v1/user/app/{id}")
    suspend fun updateStudent(@Path("id") id: String, @Body student: Student): ApiResponse<Student>

    @DELETE("api/v1/user/app/{id}")
    suspend fun deleteStudent(@Path("id") id: String): ApiResponse<Unit>

}
