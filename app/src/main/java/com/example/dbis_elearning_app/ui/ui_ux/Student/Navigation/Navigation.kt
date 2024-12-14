package com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation

import AuthScreen
import CoursePreviewScreen
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dbis_elearning_app.R
import com.example.dbis_elearning_app.SignUpScreen
import com.example.dbis_elearning_app.ui.ui_ux.Student.StudentScreen.NavBarScreens.LandingScreen
import com.example.dbis_elearning_app.ui.ui_ux.Student.StudentScreen.NavBarScreens.SearchScreen
import com.example.dbis_elearning_app.viewModel.AuthViewModel
import com.example.dbis_elearning_app.viewModel.StudentViewModel.StudentNavigationViewModel

@Composable
fun StudentApp() {
    val activity = LocalContext.current as Activity
    val navController = rememberNavController()
     NavHost(navController = navController, startDestination = ScrLoginScreen){
    composable<ScrStuLoginStage1> {SignUpScreen(onSignUp = {navController.navigate(ScrMainLandingScr)}) }
    composable<ScrStuLoginStage2> { }
    composable<ScrMainLandingScr> {
        val studentNavigationViewModel: StudentNavigationViewModel = viewModel()
        LandingScreen(studentNavigationViewModel = studentNavigationViewModel, navController = navController)
    }
     composable<ScrLoginScreen> {
             val authViewModel: AuthViewModel = hiltViewModel()
             val user by remember { authViewModel.student }
             AuthScreen(student = user, onLogin = {authViewModel.login(activity,
                 onFailure = {

             }, onSuccess = {

             })}, navController = navController,
                 onLogout = {authViewModel.logout(activity)})
         }
    composable<ScrCoursePreviewScr> {
        val imageUrl = stringResource(id = R.string.courseImage3)
        val videoUrl = " "
        CoursePreviewScreen(courseImageUrl = imageUrl, videoUrl = videoUrl,navController)
    }
}
}