package com.example.dbis_elearning_app.viewModel.InstructorViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dbis_elearning_app.data.instructor.repository.InstructorVideoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class InstructorVideoViewModel @Inject constructor(
    private val instVideoRepository: InstructorVideoRepo
) : ViewModel() {

    val uploadStatus = MutableLiveData<String>()

    // For instructor: Upload video
    fun uploadVideo(videoFile: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                val response = instVideoRepository.uploadVideo(videoFile)
                if (response.isSuccessful && response.body() != null) {
                    uploadStatus.value = "Upload successful! URL: ${response.body()!!.videoUrl}"
                } else {
                    uploadStatus.value = "Upload failed: ${response.errorBody()?.string() ?: "Unknown error"}"
                }
            } catch (e: Exception) {
                uploadStatus.value = "Upload failed: ${e.localizedMessage}"
            }
        }
    }
}