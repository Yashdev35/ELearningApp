package com.example.dbis_elearning_app.ui.ui_ux.CommonComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.InsertChart
import androidx.compose.material.icons.outlined.PersonPin
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dbis_elearning_app.ui.HelperModel.BottomNavigationItem
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation.HomeScreen
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation.ScrDashboardStu
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation.ScrSearchScreenStu

@Composable
fun DBIS_Custom_BottomBar(
    modifier: Modifier = Modifier
) {
    var selectedItemIndex by rememberSaveable { mutableStateOf(0)}


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
            selectedIcon = Icons.Filled.InsertChart,
            unselectedIcon = Icons.Outlined.InsertChart,
            hasNews = false
        ),
        BottomNavigationItem(
            navRoute = "learn",
            title = "Learn",
            selectedIcon = Icons.Filled.Book,
            unselectedIcon = Icons.Outlined.Book,
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
            NavigationBar(
                containerColor = Color.Black.copy(alpha = 0.9f)
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                        },
                        label = {
                            Text(text = item.title)
                        },
                        alwaysShowLabel = false,
                        icon = {
                            BadgedBox(
                                badge = {
                                    if(item.badgeCount != null) {
                                        Badge {
                                            Text(text = item.badgeCount.toString())
                                        }
                                    } else if(item.hasNews) {
                                        Badge()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        },
                        colors = NavigationBarItemColors(
                            selectedIconColor = Color.White,  // Adjust if you need a specific color
                            selectedTextColor = Color.White,  // Adjust if you need a specific color
                            selectedIndicatorColor = Color(0xFFFFCD6F).copy(alpha = 0.5f),  // Orange shade with 0.8 alpha for selected indicator
                            unselectedIconColor = Color.Gray,  // Adjust if you need a specific color
                            unselectedTextColor = Color.Gray,  // Adjust if you need a specific color
                            disabledIconColor = Color.LightGray,
                            disabledTextColor = Color.LightGray
                        )
                    )
                }
            }
}