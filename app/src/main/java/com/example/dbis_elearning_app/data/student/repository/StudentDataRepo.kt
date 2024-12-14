package com.example.dbis_elearning_app.data.student.repository

import com.example.dbis_elearning_app.data.student.model.AuthResponse
import com.example.dbis_elearning_app.data.student.model.DashboardData
import com.example.dbis_elearning_app.data.student.model.EducationLevel
import com.example.dbis_elearning_app.data.student.model.UserData

interface StuDataRepo {
    suspend fun signUpUser(name: String, email: String, password: String): ResultCus<AuthResponse>
    suspend fun signUpWithGoogle(token: String): ResultCus<AuthResponse>
    suspend fun updateEducation(educationLevel: EducationLevel): ResultCus<Unit>
    suspend fun getUserProfile(): ResultCus<UserData>
    suspend fun getDashboard(): ResultCus<DashboardData>
    fun logout()
}