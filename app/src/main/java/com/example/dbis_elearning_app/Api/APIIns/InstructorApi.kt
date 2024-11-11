package com.example.dbis_elearning_app.Api.APIIns

import com.example.dbis_elearning_app.data.model.VideoUploadResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface InstructorApi {

    @Multipart
    @POST("upload")
    suspend fun uploadVideo(@Part video: MultipartBody.Part): Response<VideoUploadResponse>

}