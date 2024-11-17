package com.example.dbis_elearning_app.Api.APIIns

import com.example.dbis_elearning_app.data.instructor.model.Instructor
import com.example.dbis_elearning_app.data.model.VideoUploadResponse
import com.example.dbis_elearning_app.data.student.model.ApiResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface InstructorApi {
    @POST("api/v1/instructor/app/signup")
    suspend fun createInstructor(@Body instructor: Instructor): Response<ApiResponse<Instructor>>

    @GET("api/v1/instructor/app/{id}")
    suspend fun getInstructor(@Path("id") id: String): Response<ApiResponse<Instructor>>

    @PUT("api/v1/instructor/app/{id}")
    suspend fun updateInstructor(@Path("id") id: String, @Body instructor: Instructor): Response<ApiResponse<Instructor>>

    @DELETE("api/v1/instructor/app/{id}")
    suspend fun deleteInstructor(@Path("id") id: String): Response<ApiResponse<Unit>>

    @Multipart
    @POST("api/v1/instructor/app/upload")
    suspend fun uploadVideo(@Part video: MultipartBody.Part): Response<VideoUploadResponse>
}
