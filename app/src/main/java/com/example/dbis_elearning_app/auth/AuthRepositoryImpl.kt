package com.example.dbis_elearning_app.auth

import android.app.Activity
import android.util.Log
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.example.dbis_elearning_app.R
import com.example.dbis_elearning_app.data.model.Student
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth0: Auth0
) : AuthRepository {
    override fun login(activity: Activity, callback: (Boolean, Student?) -> Unit) {
        WebAuthProvider.login(auth0)
        .withScheme(activity.getString(R.string.com_auth0_scheme))
        .withAudience("https://AmberDbisPro/")
        .start(activity, object : Callback<Credentials, AuthenticationException> {
            override fun onSuccess(result: Credentials) {
                val accessToken = result.accessToken
                Log.d("AuthViewModel", "Access token $accessToken")
                val idToken = result.idToken
                val client = AuthenticationAPIClient(auth0)
                Log.d("AuthViewModel", "Access token $accessToken")
                Log.d("AuthViewModel", "ID token ${result.idToken}")
                client.userInfo(result.accessToken)
                    .start(object : Callback<UserProfile, AuthenticationException> {
                        override fun onSuccess(result: UserProfile) {
                            val student = Student(idToken, accessToken)
                            Log.d("AuthViewModel", "User info ${result.name}")
                            Log.d("AuthViewModel", "User info ${student.email}")
                            callback(true, student)
                        }
                        override fun onFailure(error: AuthenticationException) {
                            Log.e("AuthViewModel", "Failed to retrieve user info", error)
                            callback(false, null)
                        }
                    })
            }
            override fun onFailure(error: AuthenticationException) {
                Log.e("AuthViewModel", "Login failed", error)
                callback(false, null)
            }
        })
    }

    override fun logout(activity: Activity, callback: (Boolean) -> Unit) {
        WebAuthProvider
            .logout(auth0)
            .withScheme(activity.getString(R.string.com_auth0_scheme))
            .start(activity, object : Callback<Void?, AuthenticationException> {
                override fun onSuccess(result: Void?) {
                    callback(true)
                }

                override fun onFailure(error: AuthenticationException) {
                    callback(false)
                }
            })
    }
}