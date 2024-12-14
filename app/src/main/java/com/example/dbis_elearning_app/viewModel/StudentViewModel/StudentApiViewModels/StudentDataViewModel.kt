package com.example.dbis_elearning_app.viewModel.StudentViewModel.StudentApiViewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dbis_elearning_app.data.student.model.AuthResponse
import com.example.dbis_elearning_app.data.student.model.EducationLevel
import com.example.dbis_elearning_app.data.student.model.UserData
import com.example.dbis_elearning_app.data.student.repository.StuDataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.dbis_elearning_app.data.student.repository.ResultCus

@HiltViewModel
class StudentDataViewModel @Inject constructor(
    private val studentDataRepository: StuDataRepo
) : ViewModel() {
    private val _updateEducationState = MutableStateFlow<ResultCus<Unit>?>(null)
    val updateEducationState = _updateEducationState.asStateFlow()
    private var _isLoading = mutableStateOf(false)
    val isLoading : State<Boolean> = _isLoading

    private val _educationUpdateState = MutableStateFlow<ResultCus<Unit>>(ResultCus.Initial())
    val educationUpdateState: StateFlow<ResultCus<Unit>> = _educationUpdateState.asStateFlow()

    private val _authState = MutableStateFlow<ResultCus<AuthResponse>>(ResultCus.Initial())
    val authState: StateFlow<ResultCus<AuthResponse>> = _authState.asStateFlow()

    private val _userProfileState = MutableStateFlow<ResultCus<UserData>>(ResultCus.Initial())
    val userProfileState: StateFlow<ResultCus<UserData>> = _userProfileState.asStateFlow()

    fun updateEducation(educationLevel: EducationLevel) {
        Log.d("EducationUpdate", "StartedFormViewModel")
        viewModelScope.launch {
            _educationUpdateState.value = ResultCus.Loading()
            _educationUpdateState.value = studentDataRepository.updateEducation(educationLevel)
        }
    }

    fun signUpUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = ResultCus.Loading()
            _authState.value = studentDataRepository.signUpUser(name, email, password)
        }
    }

    fun signUpWithGoogle(token: String) {
        viewModelScope.launch {
            _authState.value = ResultCus.Loading()
            _authState.value = studentDataRepository.signUpWithGoogle(token)
        }
    }

    fun fetchUserProfile() {
        viewModelScope.launch {
            _userProfileState.value = ResultCus.Loading()
            _userProfileState.value = studentDataRepository.getUserProfile()
        }
    }

    fun logout() {
        studentDataRepository.logout()
        _authState.value = ResultCus.Initial()
        _userProfileState.value = ResultCus.Initial()
    }
}