package com.example.dbis_elearning_app.ui_ux.UserScreens.Student.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import android.net.Uri

class StudentRegistrationViewModel : ViewModel() {
    // Basic Info
    var profilePicture by mutableStateOf<Uri?>(null)
    var name by mutableStateOf("")
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var email by mutableStateOf("")

    // Education Info
    var educationLevel by mutableStateOf("")
    var degree by mutableStateOf("")
    var studyYear by mutableStateOf("")
    var specialization by mutableStateOf("")
    var linkedinProfile by mutableStateOf("")
    var githubLink by mutableStateOf("")

    // UI State
    var passwordVisible by mutableStateOf(false)
    var confirmPasswordVisible by mutableStateOf(false)

    val educationLevelOptions = listOf(
        "High School", "Undergraduate", "Postgraduate", "Doctorate"
    )

    val specializationOptions = mapOf(
        "High School" to listOf("Science", "Commerce", "Arts"),
        "Undergraduate" to listOf("Computer Science", "Engineering", "Business", "Arts", "Science"),
        "Postgraduate" to listOf("Computer Science", "Engineering", "MBA", "Science", "Arts"),
        "Doctorate" to listOf("Computer Science", "Engineering", "Science", "Arts")
    )

    val yearOptions = mapOf(
        "High School" to listOf("11th", "12th"),
        "Undergraduate" to listOf("1st Year", "2nd Year", "3rd Year", "4th Year"),
        "Postgraduate" to listOf("1st Year", "2nd Year"),
        "Doctorate" to listOf("1st Year", "2nd Year", "3rd Year", "4th Year", "5th Year")
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
                email.isNotBlank() &&
                isPasswordValid() &&
                doPasswordsMatch()
    }

    fun isStageTwoValid(): Boolean {
        return educationLevel.isNotBlank() &&
                degree.isNotBlank() &&
                studyYear.isNotBlank() &&
                specialization.isNotBlank()
    }

    fun getAvailableYears(): List<String> {
        return yearOptions[educationLevel] ?: emptyList()
    }

    fun getAvailableSpecializations(): List<String> {
        return specializationOptions[educationLevel] ?: emptyList()
    }
}