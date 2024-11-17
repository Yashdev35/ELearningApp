package com.example.dbis_elearning_app.auth

import android.app.Activity
import com.example.dbis_elearning_app.data.model.Student


interface AuthRepository {
    fun login(activity: Activity, callback: (Boolean, Student?) -> Unit)
    fun logout(activity: Activity, callback: (Boolean) -> Unit)
}

