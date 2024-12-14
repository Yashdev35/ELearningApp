package com.example.dbis_elearning_app.ui.ui_ux.Student.StudentScreen.NavBarScreens

import LoadingDialog
import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dbis_elearning_app.R
import com.example.dbis_elearning_app.data.model.NavigationItem
import com.example.dbis_elearning_app.ui.ui_ux.CommonComponents.DBIS_Custom_BottomBar
import com.example.dbis_elearning_app.ui.ui_ux.CommonComponents.DrawerSheetContent
import com.example.dbis_elearning_app.ui.ui_ux.CommonComponents.MainDrawer
import com.example.dbis_elearning_app.ui.ui_ux.Student.StudentScreen.CartScreen
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Login.DisplayData
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.StudentScreen.Certificate
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.StudentScreen.HomePage
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.StudentScreen.StudentDashBoard
import com.example.dbis_elearning_app.viewModel.AuthViewModel
import com.example.dbis_elearning_app.viewModel.StudentViewModel.StudentNavigationViewModel


@Composable
fun LandingScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    studentNavigationViewModel: StudentNavigationViewModel,
    navController: NavController
){
    val disData = DisplayData()
    var isloading by remember{ mutableStateOf(true) }
    val context = LocalContext.current
    LaunchedEffect(Unit){
        authViewModel.login(
            context as Activity, onSuccess = {
                disData.name = it.name
                disData.email = it.email
                disData.profilepic = it.profilepic
                isloading = false
            }, onFailure = {
                isloading = false
            }
        )
    }
    if(isloading){
        LoadingDialog()
    }else{

        MainDrawer(
            sheetContent = {
                DrawerSheetContent(
                    disData
                )
            },
            mainContent = { LandScrMainCont(studentNavigationViewModel = studentNavigationViewModel,navController,
                authViewModel)},
            bottomBar = {
                DBIS_Custom_BottomBar(items = studentNavigationViewModel.items, onItemSelected =
                {itemIndex->
                    when(itemIndex){
                        0 -> {studentNavigationViewModel.navigationItem = NavigationItem.HOME}
                        1 -> {studentNavigationViewModel.navigationItem = NavigationItem.CAREER}
                        2 -> {studentNavigationViewModel.navigationItem = NavigationItem.LEARN}
                        3 -> {studentNavigationViewModel.navigationItem = NavigationItem.SEARCH}
                        4 -> {studentNavigationViewModel.navigationItem = NavigationItem.PROFILE}
                    }
                })
            },
        )
    }
}

@Composable
fun LandScrMainCont(studentNavigationViewModel: StudentNavigationViewModel, navController: NavController,
                    authViewModel: AuthViewModel){
    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        when(studentNavigationViewModel.navigationItem){
            NavigationItem.HOME -> { HomePage(navController = navController) }
            NavigationItem.CAREER -> {
                CartScreen()
            }
            NavigationItem.LEARN -> {
                MyLearningScreen()
            }
            NavigationItem.SEARCH -> {
                SearchScreen()
            }
            NavigationItem.PROFILE -> {
                val subjects = listOf("Mathematics", "Science", "English")
                val certificates = listOf(
                    Certificate(
                        courseName = "Mathematics",
                        teacherName = "Mr. John Doe",
                        completionDate = "12th August 2021",
                        certificateImageRes = R.drawable.login_page_image1
                    ),
                    Certificate(
                        courseName = "Science",
                        teacherName = "Ms. Jane Doe",
                        completionDate = "15th August 2021",
                        certificateImageRes = R.drawable.login_page_image1
                    )
                )
                val profileImageRes = R.drawable.dbis_project_logo_amber_removebg
                val userName = "John Doe"
                val userEmail ="MaaKiChutJohn@gmail.com"
                StudentDashBoard(
                    subjects = subjects,
                    certificates = certificates,
                    userProfileImageRes = profileImageRes,
                    userName = authViewModel.student.value!!.name,
                    userEmail = authViewModel.student.value!!.email,
                    onEditProfile = { }
                )}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview(){
    val navController = rememberNavController()
    LandingScreen(studentNavigationViewModel = StudentNavigationViewModel(),navController = navController)
}