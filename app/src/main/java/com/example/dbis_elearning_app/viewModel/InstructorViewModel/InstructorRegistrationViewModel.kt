package com.example.dbis_elearning_app.ui_ux.UserScreens.Instructor.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import android.net.Uri

class InstructorRegistrationViewModel : ViewModel() {
    var profilePicture by mutableStateOf<Uri?>(null)
    var name by mutableStateOf("")
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var bio by mutableStateOf("")

    var contactNumber by mutableStateOf("")
    var linkedinProfile by mutableStateOf("")
    var githubLink by mutableStateOf("")
    var qualification by mutableStateOf("")
    var achievements by mutableStateOf("")

    var passwordVisible by mutableStateOf(false)
    var confirmPasswordVisible by mutableStateOf(false)

    val qualificationOptions = listOf(
        "B.Tech", "M.Tech", "BSc", "MSc", "PhD", "Other"
    )

    fun isPasswordValid(): Boolean {
        return password.length >= 8 &&
                password.any { it.isDigit() } &&
                password.any { it.isUpperCase() } &&
                password.any { it.isLowerCase() } &&
                password.any { !it.isLetterOrDigit() }
    }

    fun doPasswordsMatch(): Boolean {
        return password == confirmPassword
    }

    fun isStageOneValid(): Boolean {
        return name.isNotBlank() &&
                username.isNotBlank() &&
                isPasswordValid() &&
                doPasswordsMatch() &&
                bio.isNotBlank()
    }

    fun isStageTwoValid(): Boolean {
        return contactNumber.isNotBlank() &&
                linkedinProfile.isNotBlank() &&
                qualification.isNotBlank()
    }
}