package com.example.dbis_elearning_app.viewModel.StudentViewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dbis_elearning_app.auth.AuthManager
import com.example.dbis_elearning_app.data.model.Student
import com.example.dbis_elearning_app.data.student.model.ApiResponse
import com.example.dbis_elearning_app.data.student.model.AuthResponse
import com.example.dbis_elearning_app.data.student.repository.ResultCus
import com.example.dbis_elearning_app.data.student.repository.StuSignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class StudentSignUpViewModel @Inject constructor(
    private val userRepository: StuSignUpRepository,
    private val authManager: AuthManager
) : ViewModel() {

    private val _isLogin = mutableStateOf(false)
    val isLogin: State<Boolean> get() = _isLogin

    private val _signUpResult = MutableLiveData<ApiResponse<Student>>()
    val signUpResult: LiveData<ApiResponse<Student>> get() = _signUpResult
    private val _authState = MutableStateFlow<ResultCus<AuthResponse>>(ResultCus.Initial())
    val authState: StateFlow<ResultCus<AuthResponse>> = _authState.asStateFlow()
    fun signUpUser(name: String, email: String, password: String) {
        Log.d("UserData", "Signing up user...")
        viewModelScope.launch {
            _authState.value = ResultCus.Loading()
            _authState.value = userRepository.signUpUser(name, email, password)
        }
    }
    fun clearAllTokens(context: Context) {
        authManager.clearAll()
        if (authManager.getAccessToken() == null && authManager.getRefreshToken() == null) {
            Log.d("UserData", "All tokens cleared")
            Toast.makeText(context, "All tokens cleared", Toast.LENGTH_SHORT).show()
        } else {
            Log.e("UserData", "Failed to clear tokens")
            Toast.makeText(context, "Failed to clear tokens", Toast.LENGTH_SHORT).show()
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
