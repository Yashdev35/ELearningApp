package com.example.dbis_elearning_app.viewModel.InstructorViewModel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dbis_elearning_app.data.instructor.model.Instructor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstructorDataViewModel @Inject constructor(
    //private val instructorRepository: InstructorRepository
) : ViewModel() {

    private var _instructorInfo = mutableStateOf(Instructor())
    val instructorInfo: State<Instructor> get() = _instructorInfo

    fun updateInstDp(picture: Uri) {
        _instructorInfo.value.picture = picture.toString()
    }

    /* TODO Fetches instructor information from the repository and updates the state.*/
    fun fetchInstructorInfo(id: String) {
        viewModelScope.launch {
            try {
//                val response = instructorRepository.getInstructor(id)
//                if (response.isSuccessful) {
//                    response.body()?.data?.let {
//                        _instructorInfo.value = it
//                    }
//                } else {
//                    Log.e("InstructorDataViewModel", "Failed to fetch instructor info: ${response.errorBody()?.string()}")
//                }
            } catch (e: Exception) {
                Log.e("InstructorDataViewModel", "An error occurred: ${e.message}")
            }
        }
    }

    /* TODO Updates the instructor information on the server.*/
    fun updateInstructorOnServer() {
        viewModelScope.launch {
            try {
//                val currentInstructor = _instructorInfo.value
//                val response = instructorRepository.updateInstructor(currentInstructor.id, currentInstructor)
//                if (response.isSuccessful) {
//                    Log.d("InstructorDataViewModel", "Instructor updated successfully.")
//                } else {
//                    Log.e("InstructorDataViewModel", "Failed to update instructor: ${response.errorBody()?.string()}")
//                }
            } catch (e: Exception) {
                Log.e("InstructorDataViewModel", "An error occurred while updating: ${e.message}")
            }
        }
    }
}
