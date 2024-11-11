package com.example.dbis_elearning_app.di

import android.content.Context
import com.auth0.android.Auth0
import com.example.dbis_elearning_app.R
import com.example.dbis_elearning_app.auth.AuthRepository
import com.example.dbis_elearning_app.auth.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuth0(@ApplicationContext context: Context): Auth0 {
        return Auth0(
            context.getString(R.string.com_auth0_client_id),
            context.getString(R.string.com_auth0_domain)
        )
    }

    @Provides
    @Singleton
    fun provideAuthRepository(auth0: Auth0): AuthRepository {
        return AuthRepositoryImpl(auth0)
    }
}