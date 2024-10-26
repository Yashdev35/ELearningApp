package com.example.dbis_elearning_app.Ui_Ux.UserScreens.Student.Navigation

import LoginScreen
import RegisterScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dbis_elearning_app.Ui_Ux.UserScreens.LoginScreens.LogoScreen

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

        }
    }
}