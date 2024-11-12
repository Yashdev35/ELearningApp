package com.example.dbis_elearning_app.ui_ux.UserScreens.Student.StudentScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dbis_elearning_app.R
import com.example.dbis_elearning_app.ui.ui_ux.CommonComponents.DBIS_Custom_BottomBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDrawer(
    sheetContent: @Composable () -> Unit = {},
    mainContent: @Composable () -> Unit = {},
    customLogo: @Composable () -> Unit = {},
    isCustomLogo: Boolean = false
) {
    val subjects = listOf("Chapter 1", "Chapter 2", "Chapter 3")
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
                    }
                )
            },
            bottomBar = {
                DBIS_Custom_BottomBar(
                    Modifier
                        .fillMaxWidth()
                        .background(color = Color.Black.copy(alpha = 0.8f))
                )
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
fun MainContentTrail(
    subjects: List<String>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Standard: 10th Grade",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                repeat(3) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Achievement",
                        tint = Color.Yellow
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Activity Calendar", style = MaterialTheme.typography.titleMedium)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray)
            ) {
                Text("Calendar Placeholder", modifier = Modifier.align(Alignment.Center))
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Subject Progress", style = MaterialTheme.typography.titleMedium)
        }

        items(subjects) { subject ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(subject, style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = 0.75f,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("75% Complete")
                }
            }
        }
    }
}

@Composable
fun SheetContentTrail(subjects: List<String>) {
    Text("Student Name", modifier = Modifier.padding(16.dp))
    Text("Grade: 10th", modifier = Modifier.padding(horizontal = 16.dp))
    Divider()
    Text("Upcoming Quizzes & Tests", modifier = Modifier.padding(16.dp))
    subjects.forEach { subject ->
        TextButton(
            onClick = { /* Handle quiz/test  selection */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(subject)
        }
    }
}

@Preview
@Composable
fun PreviewStudentDashboardScreen() {

    MainDrawer(
        customLogo = {
            Icon(Icons.Default.Menu, contentDescription = "Menu")
        },
        sheetContent = {  },
        mainContent = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {

            }
        }
    )
}