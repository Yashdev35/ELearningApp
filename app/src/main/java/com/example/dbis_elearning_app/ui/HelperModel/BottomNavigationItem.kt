package com.example.dbis_elearning_app.ui.HelperModel

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    var navRoute: Any,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)