package com.example.dbis_elearning_app.data.instructor.repository

import com.example.dbis_elearning_app.Api.APIIns.InstructorApi
import com.example.dbis_elearning_app.data.model.VideoUploadResponse
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class InstructorRepositoriesImpl  {

}

@Singleton
class InstructorVideoRepositoriesImpl @Inject constructor(
    private val instructorApi: InstructorApi
) : InstructorVideoRepo {
    override suspend fun uploadVideo(video: MultipartBody.Part): Response<VideoUploadResponse> {
        return instructorApi.uploadVideo(video)
    }
}