package com.example.dbis_elearning_app.Ui_Ux.UserScreens.Student.StudentScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursePreviewScreen() {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LearnHub") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            CourseHeader()
            WhatYoullLearn()
            PriceCard()
            CourseContent()
            Requirements()
            Instructor()
        }
    }
}

@Composable
fun CourseHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "The Complete 2024 Web Development Bootcamp",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Become a Full-Stack Web Developer with just ONE course. HTML, CSS, Javascript, Node, React, PostgreSQL, Web3 and DApps",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun WhatYoullLearn() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "What you'll learn",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            LearningPoint("Build 16 web development projects for your portfolio")
            LearningPoint("Learn the latest technologies, including Javascript, React, Node and even Web3 development")
            LearningPoint("Build fully-fledged websites and web apps for your startup or business")
            LearningPoint("Master frontend development with React")
        }
    }
}

@Composable
fun LearningPoint(text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.Check,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun PriceCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "₹3,099",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* Handle add to cart */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add to cart")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("30-Day Money-Back Guarantee", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            CourseFeature(Icons.Default.VideoLibrary, "61 hours on-demand video")
            CourseFeature(Icons.Default.Article, "65 articles")
            CourseFeature(Icons.Default.PhoneAndroid, "Access on mobile and TV")
            CourseFeature(Icons.Default.WorkspacePremium, "Certificate of completion")
        }
    }
}

@Composable
fun CourseFeature(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun CourseContent() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "Course content",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("8 sections • 110 lectures • 100m total length", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(16.dp))
            CourseSection("Front-End Web Development", "9 lectures • 37min")
            CourseSection("Introduction to HTML", "")
            CourseSection("Intermediate HTML", "")
            CourseSection("Introduction to CSS", "")
            CourseSection("Intermediate CSS", "")
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = { /* Handle show all sections */ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Show all sections")
            }
        }
    }
}

@Composable
fun CourseSection(title: String, details: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, style = MaterialTheme.typography.bodyMedium)
        if (details.isNotEmpty()) {
            Text(details, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}

@Composable
fun Requirements() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "Requirements",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            RequirementItem("No programming experience needed - I'll teach you everything you need to know")
            RequirementItem("A computer with access to the internet")
            RequirementItem("No paid software required")
            RequirementItem("I'll walk you through, step-by-step how to get all the software installed and set up")
        }
    }
}

@Composable
fun RequirementItem(text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            Icons.Default.FiberManualRecord,
            contentDescription = null,
            modifier = Modifier.size(8.dp).padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun Instructor() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "Instructor",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // You can replace this with an actual image
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        "Dr. Angela Yu",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Developer and Lead Instructor",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.Yellow,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "4.7 Instructor Rating",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Text(
                        "2,918,393 Students",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        "7 Courses",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
@Preview
@Composable
fun PreviewCoursePreviewScreen() {
    CoursePreviewScreen()
}