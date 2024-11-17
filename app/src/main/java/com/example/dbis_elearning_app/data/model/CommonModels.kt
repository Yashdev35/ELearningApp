package com.example.dbis_elearning_app.data.model

import com.auth0.android.jwt.JWT


data class VideoUploadResponse(
    val message: String,
    val videoUrl: String
)
data class Student(val idToken: String? = null, val accessToken: String? = null) {

    var id = ""
    var name = ""
    var email = ""
    var emailVerified = ""
    var picture = ""
    var updatedAt = ""
    var password: String = ""
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

enum class NavigationItem(val title : String , val ind : Int) {
    HOME("Home", 0),
    CAREER("Career", 1),
    LEARN("Learn", 2),
    SEARCH("Search", 3),
    PROFILE("Profile", 4)
}

