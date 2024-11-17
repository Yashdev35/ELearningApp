package com.example.dbis_elearning_app.ui_ux.UserScreens.Student.StudentScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dbis_elearning_app.R
import com.example.dbis_elearning_app.ui.ui_ux.CommonComponents.ActivityCalendar
import com.example.dbis_elearning_app.ui.ui_ux.CommonComponents.MainDrawer
import com.example.dbis_elearning_app.ui.ui_ux.CommonComponents.ProfileSection

// Define custom colors for the black and white theme
val BlackTransparent = Color.Black.copy(alpha = 0.95f)
val PureWhite = Color.White
val LightGray = Color.LightGray

data class Certificate(
    val courseName: String,
    val teacherName: String,
    val completionDate: String,
    val certificateImageRes: Int
)

@Composable
fun StudentDashBoard(
    subjects: List<String>,
    certificates: List<Certificate>,
    userProfileImageRes: Int,
    userName: String,
    userEmail: String,
    onEditProfile: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BlackTransparent
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                ProfileSection(
                    profileImageRes = userProfileImageRes,
                    userName = userName,
                    userEmail = userEmail,
                    onEditProfile = onEditProfile,
                    isEditProfile = false,
                    onProfileImageChanged = {}
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                ActivityCalendar()
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text("Completed Courses", style = MaterialTheme.typography.titleMedium, color = PureWhite)
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(certificates) { certificate ->
                ExpandableCertificateCard(certificate = certificate)
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Subject Progress", style = MaterialTheme.typography.titleMedium, color = PureWhite)
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(subjects) { subject ->
                SubjectProgressCard(subject = subject)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ExpandableCertificateCard(certificate: Certificate) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = certificate.courseName,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                    color = Color.White.copy(alpha = 0.8f)
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Collapse" else "Expand",
                        tint = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = certificate.certificateImageRes),
                    contentDescription = "Certificate",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Teacher: ${certificate.teacherName}", color = Color.White)
                Text("Completed on: ${certificate.completionDate}", color = Color.White)
            }
        }
    }
}

@Composable
fun SubjectProgressCard(subject: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(subject, style = MaterialTheme.typography.titleSmall, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = 0.75f,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White.copy(alpha = 0.8f),
                trackColor = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("75% Complete", color = Color.White.copy(alpha = 0.8f))
        }
    }
}

@Preview
@Composable
fun PreviewStudentDashboardScreen() {
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

    MainDrawer(
        customLogo = {
            Icon(Icons.Default.Menu, contentDescription = "Menu")
        },
        sheetContent = {  },
        mainContent = {
            StudentDashBoard(
                subjects = subjects,
                certificates = certificates,
                userProfileImageRes = profileImageRes,
                userName = userName,
                userEmail = userEmail,
                onEditProfile = { }
            )
        }
    )
}