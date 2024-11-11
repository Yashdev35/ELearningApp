package com.example.dbis_elearning_app.data.model

import com.auth0.android.jwt.JWT


data class VideoUploadResponse(
    val message: String,
    val videoUrl: String
)
data class User(val idToken: String? = null) {

    var id = ""
    var name = ""
    var email = ""
    var emailVerified = ""
    var picture = ""
    var updatedAt = ""

    init {
        try {
            val jwt = JWT(idToken ?: "")
            id = jwt.subject ?: ""
            name = jwt.getClaim("name").asString() ?: ""
            email = jwt.getClaim("email").asString() ?: ""
            emailVerified = jwt.getClaim("email_verified").asString() ?: ""
            picture = jwt.getClaim("picture").asString() ?: ""
            updatedAt = jwt.getClaim("updated_at").asString() ?: ""
        } catch (e: com.auth0.android.jwt.DecodeException) {
            // Handle invalid token
        }
    }
}

