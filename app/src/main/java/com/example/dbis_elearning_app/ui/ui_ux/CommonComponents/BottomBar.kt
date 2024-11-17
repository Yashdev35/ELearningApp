package com.example.dbis_elearning_app.ui.ui_ux.CommonComponents

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.dbis_elearning_app.ui.HelperModel.BottomNavigationItem

@Composable
fun DBIS_Custom_BottomBar(
    items: List<BottomNavigationItem>,
    onItemSelected: (Int) -> Unit
) {
    var selectedItemIndex by rememberSaveable { mutableStateOf(0)}

            NavigationBar(
                containerColor = Color(0xFF191819).copy(alpha = 0.9f),
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            onItemSelected(index)
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