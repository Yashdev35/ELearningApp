package com.example.dbis_elearning_app.ui_ux.UserScreens

import LoginScreen
import RegisterScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dbis_elearning_app.ui_ux.UserScreens.Instructor.Navigation.InstructorApp
import com.example.dbis_elearning_app.ui_ux.UserScreens.Instructor.Navigation.ScrInstructorApp
import com.example.dbis_elearning_app.ui_ux.UserScreens.LoginScreens.AccountTypeScreen
import com.example.dbis_elearning_app.ui_ux.UserScreens.LoginScreens.LogoScreen
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation.ScrAccountTypeSelector
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation.ScrLoginScreen
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation.ScrLogo
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation.ScrRegisterScreen
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation.ScrStudentApp
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation.StudentApp

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = ScrLogo){
        composable<ScrLogo> {
            LogoScreen(
                onTimeout = { navController.navigate(ScrLoginScreen) },
                isUserDataLoaded = { return@LogoScreen false }
            )
        }
        composable<ScrLoginScreen> {
            LoginScreen(navController = navController)
        }
        composable<ScrRegisterScreen> {
            RegisterScreen(navController = navController)
        }
        composable<ScrAccountTypeSelector> {
            AccountTypeScreen(onTypeSelected = { type ->
                when(type){
                    "Student" -> navController.navigate(ScrStudentApp)
                    "Instructor" -> navController.navigate(ScrInstructorApp)
                }
            }, onBackPressed = {
                navController.popBackStack()
            },
        )
        }
        composable<ScrStudentApp> {
            StudentApp(navController = navController)
        }
        composable<ScrInstructorApp> {
            InstructorApp()
        }
    }
}

@Preview
@Composable
fun AppNavigationPreview() {
    val navController = rememberNavController()
    AppNavigation(navController = navController)
}