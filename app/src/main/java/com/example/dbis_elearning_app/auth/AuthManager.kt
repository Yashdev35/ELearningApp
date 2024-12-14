package com.example.dbis_elearning_app.auth

import android.content.Context
import android.util.Log
import com.example.dbis_elearning_app.data.student.model.UserData
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val PREFS_NAME = "AuthPrefs"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_COOKIE_TOKEN = "cookie_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_USER_DATA = "user_data"
    }
    val gson = Gson()
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    suspend fun saveAuthData(userData: UserData, accessToken: String, refreshToken: String, cookieToken: String) {
        withContext(Dispatchers.IO) {
            // Also save to SharedPreferences for backward compatibility
            prefs.edit().apply {
                putString(KEY_ACCESS_TOKEN, accessToken)
                putString(KEY_REFRESH_TOKEN, refreshToken)
                putString(KEY_COOKIE_TOKEN, cookieToken)
                putString(KEY_USER_DATA, gson.toJson(userData))
                apply()
            }
        }
    }

    fun saveAccessToken(token: String) {
        prefs.edit().putString(KEY_ACCESS_TOKEN, token).apply()
    }

    fun saveCookieToken(token: String) {
        prefs.edit().putString(KEY_COOKIE_TOKEN, token).apply()
    }

    fun saveRefreshToken(token: String) {
        prefs.edit().putString(KEY_REFRESH_TOKEN, token).apply()
    }

    fun saveUserData(userData: UserData) {
        prefs.edit().putString(KEY_USER_DATA, gson.toJson(userData)).apply()
    }

    fun getAccessToken(): String? = prefs.getString(KEY_ACCESS_TOKEN, null)
    fun getCookieToken(): String? = prefs.getString(KEY_COOKIE_TOKEN, null)
    fun getRefreshToken(): String? = prefs.getString(KEY_REFRESH_TOKEN, null)

    fun getUserData(): UserData? {
        return prefs.getString(KEY_USER_DATA, null)?.let {
            gson.fromJson(it, UserData::class.java)
        }
    }

    fun clearAll() {
        prefs.edit().clear().apply()
    }

    fun isAuthenticated(): Boolean {
        return getAccessToken() != null || getCookieToken() != null
    }
    fun displayAccessToken() {
        Log.d("UserData", "Access Token: ${getAccessToken()}")
    }
    fun AuthManager.getFormattedCookieHeader(): String? {
        return getCookieToken()?.let { "accessToken=$it" }
    }

    fun AuthManager.getAuthorizationHeader(): String? {
        return getAccessToken()?.let { "Bearer $it" }
    }
//    suspend fun getAuthData(): AuthEntity? = withContext(Dispatchers.IO) { authDao.getAuthData() }

//    suspend fun clearAuthData() {
//        withContext(Dispatchers.IO) {
//            authDao.clearAuthData()
//            prefs.edit().clear().apply()
//        }
//    }

    //suspend fun isAuthenticatedByRoom(): Boolean = getAuthData() != null
}