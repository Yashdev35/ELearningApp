package com.example.dbis_elearning_app.auth

import android.app.Activity
import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.example.dbis_elearning_app.R
import com.example.dbis_elearning_app.data.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth0: Auth0
) : AuthRepository {

    override fun login(activity: Activity, callback: (Boolean, User?) -> Unit) {
        WebAuthProvider
            .login(auth0)
            .withScheme(activity.getString(R.string.com_auth0_scheme))
            .start(activity, object : Callback<Credentials, AuthenticationException> {
                override fun onSuccess(credentials: Credentials) {
                    val user = User(credentials.idToken)
                    callback(true, user)
                }

                override fun onFailure(exception: AuthenticationException) {
                    callback(false, null)
                }
            })
    }

    override fun logout(activity: Activity, callback: (Boolean) -> Unit) {
        WebAuthProvider
            .logout(auth0)
            .withScheme(activity.getString(R.string.com_auth0_scheme))
            .start(activity, object : Callback<Void?, AuthenticationException> {
                override fun onSuccess(payload: Void?) {
                    callback(true)
                }

                override fun onFailure(exception: AuthenticationException) {
                    callback(false)
                }
            })
    }
}



