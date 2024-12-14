package com.example.dbis_elearning_app.UseFultrialCode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.dbis_elearning_app.data.student.model.DashboardData
import com.example.dbis_elearning_app.data.student.model.Purchase
import com.example.dbis_elearning_app.data.student.repository.ResultCus
import com.example.dbis_elearning_app.viewModel.uiViewModels.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val dashboardState by viewModel.dashboardState.collectAsState()

    when (dashboardState) {
        is ResultCus.Initial -> {
            // Show nothing or initial state
        }
        is ResultCus.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is ResultCus.Success -> {
            val dashboard = (dashboardState as ResultCus.Success<DashboardData>).data
            DashboardContent(dashboard)
        }
        is ResultCus.Error -> {
            val error = (dashboardState as ResultCus.Error<DashboardData>).exception
            ErrorScreen(
                message = error.message ?: "An error occurred",
                onRetry = { viewModel.fetchDashboard() }
            )
        }
    }
}

@Composable
fun DashboardContent(dashboard: DashboardData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // User Profile Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = dashboard.name,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = dashboard.email,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Profile Picture
            AsyncImage(
                model = dashboard.profilepicture ?: "/api/placeholder/100/100",
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Education Details
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Education Details",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                dashboard.educationLevel?.let {
                    Text("Education Level: $it")
                }
                dashboard.studyYear?.let {
                    Text("Study Year: $it")
                }
                dashboard.degree?.let {
                    Text("Degree: $it")
                }
                dashboard.specialization?.let {
                    Text("Specialization: $it")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Purchased Courses
        Text(
            text = "My Courses",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(dashboard.purchases) { purchase ->
                CourseItem(purchase)
            }
        }
    }
}

@Composable
fun CourseItem(purchase: Purchase) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = purchase.course.imageUrl,
                contentDescription = "Course Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = purchase.course.title,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Assigned: ${purchase.assignedAt}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun ErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}