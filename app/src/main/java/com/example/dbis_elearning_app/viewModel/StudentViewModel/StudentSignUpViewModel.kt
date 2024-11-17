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
import javax.inject.Inject

@HiltViewModel
class StudentSignUpViewModel @Inject constructor(
    private val userRepository: StuSignUpRepository
) : ViewModel() {
    private val _isLogin = mutableStateOf(false)
    val isLogin: State<Boolean> = _isLogin
    private val _signUpResult = MutableLiveData<ApiResponse<Student>>()
    val signUpResult: LiveData<ApiResponse<Student>> = _signUpResult

     suspend fun signUpUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLogin.value = true
                Log.d("SignUp", "Signing up user...")
                val request = UserSignUpRequest(name, email, password)
                val response = userRepository.signUpUser(request)
                _signUpResult.postValue(response)
                _isLogin.value = false
                Log.d("SignUp", "User created successfully: ${response.data?.name}")
            } catch (e: Exception) {
                _isLogin.value = false
                _signUpResult.postValue(ApiResponse(500, null, "An error occurred: ${e.message}"))
                Log.e("SignUp", "An error occurred: ${e.message}")
            }
        }
    }

    suspend fun signUpUserWith(accessToken: String) {
        viewModelScope.launch {
            try {
                _isLogin.value = true
                Log.d("SignUp", "Signing up user...")
                val response = userRepository.signUpUserWith(accessToken)
                _signUpResult.postValue(response)
                _isLogin.value = false
                Log.d("SignUp", "User created successfully: ${response.data?.name}")
            } catch (e: Exception) {
                _isLogin.value = false
                _signUpResult.postValue(ApiResponse(500, null, "An error occurred: ${e.message}"))
                Log.e("SignUp", "An error occurred: ${e.message}")
            }
        }
    }
}