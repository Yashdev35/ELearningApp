package com.example.dbis_elearning_app.ui_ux.UserScreens.Instructor.InstructorScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

data class Course(val name: String, val newStudents: Int, val totalHours: Int, val totalStudents: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnHubDashboardScreen() {
    val courses = listOf(
        Course("Introduction to Machine Learning", 45, 40, 150),
        Course("Advanced Python Programming", 38, 50, 120),
        Course("Data Visualization Techniques", 22, 35, 90),
        Course("Deep Learning Fundamentals", 30, 60, 80),
        Course("Natural Language Processing", 25, 65, 100)
    )

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Dr. Jane Smith", modifier = Modifier.padding(16.dp))
                Text(
                    "Ph.D. in Computer Science, M.Sc. in Data Science",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                HorizontalDivider()
                Text(
                    "Total Content Provided: 250 hours",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Learn Hub Dashboard") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        })
                        {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        TextButton(onClick = { /* Handle new course creation */ }) {
                            Text("Create New Course")
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(courses) { course ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(course.name, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("${course.newStudents} new students this month")
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(onClick = { /* Handle view course */ }) {
                                    Text("View Course")
                                }
                                Text("${course.totalHours} hours")
                                Text("${course.totalStudents} total")
                            }
                        }
                    }
                }
            }
        }
    }

}
@Preview
@Composable
fun PreviewLearnHubDashboardScreen() {
    LearnHubDashboardScreen()
}