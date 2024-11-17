package com.example.dbis_elearning_app.viewModel.StudentViewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.PersonPin
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dbis_elearning_app.data.model.NavigationItem
import com.example.dbis_elearning_app.ui.HelperModel.BottomNavigationItem
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation.HomeScreen
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation.ScrDashboardStu
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation.ScrSearchScreenStu


class StudentNavigationViewModel : ViewModel() {
    val items = listOf(
        BottomNavigationItem(
            navRoute = HomeScreen,
            title = "Home",
            selectedIcon = Icons.Filled.Explore,
            unselectedIcon = Icons.Outlined.Explore,
            hasNews = true,
            badgeCount = 5
        ),
        BottomNavigationItem(
            navRoute = HomeScreen,
            title = "Career",
            selectedIcon = Icons.Filled.ShoppingCart,
            unselectedIcon = Icons.Outlined.ShoppingCart,
            hasNews = false
        ),
        BottomNavigationItem(
            navRoute = "learn",
            title = "Learn",
            selectedIcon = Icons.AutoMirrored.Filled.MenuBook,
            unselectedIcon = Icons.AutoMirrored.Outlined.MenuBook,
            hasNews = false
        ),
        BottomNavigationItem(
            navRoute = ScrSearchScreenStu,
            title = "Search",
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
            hasNews = false
        ),
        BottomNavigationItem(
            navRoute = ScrDashboardStu,
            title = "Profile",
            selectedIcon = Icons.Filled.PersonPin,
            unselectedIcon = Icons.Outlined.PersonPin,
            hasNews = false
        )
    )
    var navigationItem by mutableStateOf(NavigationItem.HOME)
}