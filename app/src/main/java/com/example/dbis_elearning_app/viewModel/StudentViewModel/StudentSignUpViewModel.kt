package com.example.dbis_elearning_app.viewModel.StudentViewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dbis_elearning_app.data.model.Student
import com.example.dbis_elearning_app.data.student.model.ApiResponse
import com.example.dbis_elearning_app.data.student.model.UserSignUpRequest
import com.example.dbis_elearning_app.data.student.repository.StuSignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class StudentSignUpViewModel @Inject constructor(
    private val userRepository: StuSignUpRepository
) : ViewModel() {

    private val _isLogin = mutableStateOf(false)
    val isLogin: State<Boolean> get() = _isLogin

    private val _signUpResult = MutableLiveData<ApiResponse<Student>>()
    val signUpResult: LiveData<ApiResponse<Student>> get() = _signUpResult

    fun signUpUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            _isLogin.value = true
            try {
                Log.d("SignUp", "Initiating user sign-up...")
                val request = UserSignUpRequest(name, email, password)
                val response = userRepository.signUpUser(request)

                handleApiResponse(response)
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLogin.value = false
            }
        }
    }

    fun signUpUserWith(accessToken: String) {
        viewModelScope.launch {
            _isLogin.value = true
            try {
                Log.d("SignUp", "Signing up user with access token...")
                val response = userRepository.signUpUserWith(accessToken)

                handleApiResponse(response)
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLogin.value = false
            }
        }
    }

    private fun handleApiResponse(response: Response<ApiResponse<Student>>) {
        if (response.isSuccessful) {
            val apiResponse = response.body()
            if (apiResponse?.statusCode == 200) {
                Log.d("SignUp", "User signed up successfully: ${apiResponse.data?.name}")
                _signUpResult.postValue(apiResponse)
            } else {
                Log.e("SignUp", "API Error: ${apiResponse?.message}")
                _signUpResult.postValue(ApiResponse(apiResponse?.statusCode ?: 400, null, apiResponse?.message ?: "Unknown error"))
            }
        } else {
            val errorMessage = response.errorBody()?.string() ?: "Unknown server error"
            Log.e("SignUp", "Server Error: $errorMessage")
            _signUpResult.postValue(ApiResponse(response.code(), null, errorMessage))
        }
    }

    private fun handleError(e: Exception) {
        Log.e("SignUp", "An exception occurred: ${e.message}")
        _signUpResult.postValue(ApiResponse(500, null, "An error occurred: ${e.message}"))
    }
}
