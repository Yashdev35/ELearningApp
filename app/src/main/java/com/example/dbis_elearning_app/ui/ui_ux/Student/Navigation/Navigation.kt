package com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation

import CoursePreviewScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dbis_elearning_app.R
import com.example.dbis_elearning_app.ui.ui_ux.Student.StudentScreen.NavBarScreens.LandingScreen
import com.example.dbis_elearning_app.viewModel.StudentViewModel.StudentNavigationViewModel

@Composable
fun StudentApp() {
    val navController = rememberNavController()
     NavHost(navController = navController, startDestination = ScrMainLandingScr){
    composable<ScrStuLoginStage1> { }
    composable<ScrStuLoginStage2> { }
    composable<ScrMainLandingScr> {
        val studentNavigationViewModel: StudentNavigationViewModel = viewModel()
        LandingScreen(studentNavigationViewModel = studentNavigationViewModel,navController)
    }
    composable<ScrCoursePreviewScr> {
        val imageUrl = stringResource(id = R.string.courseImage3)
        val videoUrl = " "
        CoursePreviewScreen(courseImageUrl = imageUrl, videoUrl = videoUrl,navController)
    }
}
}