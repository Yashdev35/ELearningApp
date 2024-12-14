package com.example.dbis_elearning_app.di

import com.example.dbis_elearning_app.Api.APIIns.InstructorApi
import com.example.dbis_elearning_app.Api.APIStu.StudentApi
import com.example.dbis_elearning_app.auth.AuthManager
import com.example.dbis_elearning_app.data.instructor.repository.InstructorVideoRepo
import com.example.dbis_elearning_app.data.instructor.repository.InstructorVideoRepositoriesImpl
import com.example.dbis_elearning_app.data.student.repository.StuDataRepo
import com.example.dbis_elearning_app.data.student.repository.StuSignUpRepositoryImpl
import com.example.dbis_elearning_app.data.student.repository.StudentDataRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideInstructorVideoRepository(
        instructorApi: InstructorApi
    ): InstructorVideoRepo {
        return InstructorVideoRepositoriesImpl(instructorApi)
    }

    @Provides
    @Singleton
    fun provideStudentDataRepo(
        studentApi: StudentApi,
        authManager: AuthManager
    ): StuDataRepo {
        return StudentDataRepoImpl(studentApi,authManager)
    }

}