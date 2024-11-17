package com.example.dbis_elearning_app.viewModel

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dbis_elearning_app.auth.AuthRepository
import com.example.dbis_elearning_app.data.model.Student
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _student = mutableStateOf<Student?>(null)
    val student: State<Student?> = _student


    fun login(activity: Activity) {
        authRepository.login(activity) { success, user ->
            if (success) {
                _student.value = user
                Log.d("AuthViewModel", user!!.name)
            } else {
                Log.d("AuthViewModel", "Failed to login")
                _student.value = null
            }
        }
    }

    fun logout(activity: Activity) {
        val user = _student.value
        authRepository.logout(activity) { success ->
            if (success) {
                Log.d("AuthViewModel", "Logged out")
                _student.value = null
            }else {
                _student.value = user
            }
        }
    }
}
