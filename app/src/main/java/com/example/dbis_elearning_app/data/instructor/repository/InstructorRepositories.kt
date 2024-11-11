package com.example.dbis_elearning_app.data.instructor.repository

import com.example.dbis_elearning_app.data.model.VideoUploadResponse
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Singleton

interface InstructorRepo {
}


interface InstructorVideoRepo{
    suspend fun uploadVideo(video: MultipartBody.Part): Response<VideoUploadResponse>
}