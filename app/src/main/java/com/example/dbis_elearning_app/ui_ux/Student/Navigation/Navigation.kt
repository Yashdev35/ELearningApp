package com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun StudentApp(
    navController: NavHostController,
) {
NavHost(navController = navController, startDestination = ScrStuLoginStage1){
    composable<ScrStuLoginStage1> { }
}
}