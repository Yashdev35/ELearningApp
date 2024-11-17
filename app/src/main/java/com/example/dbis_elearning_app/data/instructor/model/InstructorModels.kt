package com.example.dbis_elearning_app.data.instructor.model

import com.auth0.android.jwt.JWT

data class Instructor(
    val idToken: String? = null,
    val accessToken: String? = null
) {
    var id: String = ""
    var name: String = ""
    var email: String = ""
    var emailVerified: String = ""
    var picture: String = ""
    var updatedAt: String = ""
    var password: String = "" // New field for password

    init {
        try {
            idToken?.let {
                val jwt = JWT(it)
                id = jwt.subject ?: ""
                name = jwt.getClaim("name").asString() ?: ""
                email = jwt.getClaim("email").asString() ?: ""
                emailVerified = jwt.getClaim("email_verified").asString() ?: ""
                picture = jwt.getClaim("picture").asString() ?: ""
                updatedAt = jwt.getClaim("updated_at").asString() ?: ""
            }
        } catch (e: com.auth0.android.jwt.DecodeException) {
            // Handle invalid token
        }
    }
}