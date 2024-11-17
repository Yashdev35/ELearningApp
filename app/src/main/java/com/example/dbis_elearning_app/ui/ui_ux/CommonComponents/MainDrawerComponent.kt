package com.example.dbis_elearning_app.ui.ui_ux.CommonComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContactPage
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.dbis_elearning_app.R
import com.example.dbis_elearning_app.ui.theme.darkColorScheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDrawer(
    sheetContent: @Composable () -> Unit = {},
    mainContent: @Composable () -> Unit = {},
    customLogo: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    isCustomLogo: Boolean = false
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                sheetContent()
            }
        }) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch(){
                            drawerState.apply { if (isClosed) open() else close() }
                        } }) {
                            if (isCustomLogo) {
                                customLogo()
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.dbis_project_logo_amber_removebg),
                                    contentDescription = "Menu",
                                    modifier = Modifier.fillMaxHeight()
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors().copy(
                        containerColor = Color(0xFF0D0D0D),
                    ),
                )
            },
            bottomBar = {
               bottomBar()
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                mainContent()
            }
        }
    }
}

@Composable
fun DrawerSheetContent(
    onNavigateToSettings: () -> Unit = {},
    onNavigateToAccountSettings: () -> Unit = {},
    onNavigateToContactDetails: () -> Unit = {},
    onSwitchToInstructor: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkColorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Section
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(darkColorScheme.surface)
            ) {
                Image(
                    painter = rememberAsyncImagePainter("https://placeholder.com/user.jpg"),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "John Doe",
                style = MaterialTheme.typography.titleLarge,
                color = darkColorScheme.onBackground
            )

            Text(
                text = "john.doe@example.com",
                style = MaterialTheme.typography.bodyMedium,
                color = darkColorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Menu Options
            DrawerMenuItem(
                icon = Icons.Default.Settings,
                text = "Settings",
                onClick = onNavigateToSettings
            )

            DrawerMenuItem(
                icon = Icons.Default.ManageAccounts,
                text = "Account Settings",
                onClick = onNavigateToAccountSettings
            )

            DrawerMenuItem(
                icon = Icons.Default.ContactPage,
                text = "Contact Details",
                onClick = onNavigateToContactDetails
            )
        }

        // Bottom Options
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Divider(
                color = darkColorScheme.onBackground.copy(alpha = 0.12f),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(16.dp))

            DrawerMenuItem(
                icon = Icons.Default.School,
                text = "Switch to Instructor",
                onClick = onSwitchToInstructor
            )

            DrawerMenuItem(
                icon = Icons.Default.Logout,
                text = "Logout",
                onClick = onLogout,
                tint = Color.Red
            )
        }
    }
}

@Composable
private fun DrawerMenuItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    tint: Color = darkColorScheme.onBackground
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = tint
            )
        }
    }
}