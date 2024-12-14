package com.example.dbis_elearning_app.viewModel

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dbis_elearning_app.auth.AuthManager
import com.example.dbis_elearning_app.auth.AuthRepository
import com.example.dbis_elearning_app.data.model.Student
import com.example.dbis_elearning_app.data.student.repository.StuDataRepo
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Login.DisplayData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val stuDataRepo: StuDataRepo,
    private val authManager: AuthManager
) : ViewModel() {
    private val _student = mutableStateOf<Student?>(null)
    val student: State<Student?> = _student
    private var _isLoading = mutableStateOf<Boolean>(false)
    val isLoading :State<Boolean> = _isLoading

    fun login(activity: Activity, onSuccess: (DisplayData) -> Unit = {}, onFailure: () -> Unit = {}) {
        _isLoading.value = true
        authRepository.login(activity) { success, user ->
            if (success) {
                _isLoading.value = false
                _student.value = user
                Log.d("AuthViewModel", user!!.name)
                Log.d("AuthViewModel",student.value!!.toString())
                viewModelScope.launch{
                    stuDataRepo.signUpWithGoogle(user.accessToken.toString())
                    val disData = DisplayData(
                        name = student.value!!.name,
                        email = student.value!!.email,
                        profilepic = student.value!!.picture
                    )
                    onSuccess(disData)
                }
            } else {
                _isLoading.value = false
                Log.d("AuthViewModel", "Failed to login")
                onFailure()
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
