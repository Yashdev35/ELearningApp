package com.example.dbis_elearning_app.viewModel

import android.app.Activity
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dbis_elearning_app.auth.AuthRepository
import com.example.dbis_elearning_app.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> = _user

    fun login(activity: Activity) {
        authRepository.login(activity) { success, user ->
            if (success) {
                _user.value = user
            } else {
                _user.value = null
            }
        }
    }

    fun logout(activity: Activity) {
        val user = _user.value
        authRepository.logout(activity) { success ->
            if (success) {
                _user.value = null
            }else {
                _user.value = user
            }
        }
    }
}
