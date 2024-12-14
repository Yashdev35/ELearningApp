package com.example.dbis_elearning_app.data.student.model

// Create data classes to handle the response
data class ApiResponseDash<T>(
    val statusCode: Int,
    val data: T,
    val message: String
)

data class DashboardData(
    val id: String,
    val name: String,
    val username: String,
    val email: String,
    val profilepicture: String?,
    val educationLevel: String?,
    val schoolingYear: String?,
    val schoolStream: String?,
    val degree: String?,
    val studyYear: String?,
    val specialization: String?,
    val createdAt: String,
    val updatedAt: String,
    val purchases: List<Purchase>
)

data class Purchase(
    val assignedAt: String,
    val course: Course
)

data class Course(
    val id: String,
    val title: String,
    val imageUrl: String
)