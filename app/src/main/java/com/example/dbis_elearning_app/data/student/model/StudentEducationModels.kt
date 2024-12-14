package com.example.dbis_elearning_app.data.student.model

import com.google.gson.annotations.SerializedName

data class DynamicEducationRequest(
    val educationLevel: String,
    val fields: Map<String, String>
)

data class EducationLevel(
    var level1: String = "",
    var level2: String = "",
    var level3: String = "",
    var level4: String = ""
)

data class StudentData(
    val profilePictureUrl: String,
    val name: String,
    val email: String,
    val educationLevel: EducationLevel
)
data class SchoolRequest(
    @SerializedName("educationLevel") val educationLevel: String = "SCHOOL",
    @SerializedName("schoolingYear") val schoolingYear: String,
    @SerializedName("schoolStream") val schoolStream: String
)

data class UndergraduateRequest(
    @SerializedName("educationLevel") val educationLevel: String = "UNDERGRADUATE",
    @SerializedName("studyYear") val studyYear: String,
    @SerializedName("degree") val degree: String
)

data class PostgraduateRequest(
    @SerializedName("educationLevel") val educationLevel: String = "POSTGRADUATE",
    @SerializedName("studyYear") val studyYear: String,
    @SerializedName("degree") val degree: String,
    @SerializedName("specialization") val specialization: String
)

data class UserResponseData(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("profilepicture") val profilepicture: String?,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)

// Data class for tokens in the message
data class TokenMessage(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)

// Main auth response class
data class AuthResponse(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("data") val data: UserResponseData,
    @SerializedName("message") val message: TokenMessage,
    @SerializedName("success") val success: Boolean
)

data class UserData(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("profilepicture") val profilePicture: String?,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("accessToken")val accessToken: String,
    @SerializedName("refreshToken")val refreshToken: String,
)

// api/model/request/UpdateUserRequest.kt
data class UpdateUserRequest(
    @SerializedName("education") val education: String
)