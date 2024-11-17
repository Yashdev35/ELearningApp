package com.example.dbis_elearning_app.ui_ux.UserScreens.Student.StudentScreen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.dbis_elearning_app.R
import com.example.dbis_elearning_app.ui.theme.TextCyan
import com.example.dbis_elearning_app.ui.ui_ux.CommonComponents.CategoryCard
import com.example.dbis_elearning_app.ui.ui_ux.CommonComponents.CourseCard

data class Course(
    val id: Int,
    val title: String,
    val instructor: String,
    val rating: Float,
    val reviews: Int,
    val price: Double,
    val imageUrl: String
)

data class Category(
    val id: Int,
    val name: String,
    val icon: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController) {
    val courses =
        listOf(
            Course(1, "Complete Web Development Bootcamp", "John Doe", 4.7f, 1234, 199.99, imageUrl = stringResource(id = R.string.HomeScreenCourseImage1)),
            Course(2, "Data Science Fundamentals", "Jane Smith", 4.8f, 890, 149.99, imageUrl = stringResource(id = R.string.HomeScreenCourseImage2)),
            Course(3, "Mobile App Development", "Mike Johnson", 4.6f, 567, 179.99, imageUrl =  stringResource(id = R.string.HomeScreenCourseImage2))
        )

    val categories = remember {
        listOf(
            Category(1, "Web Development", "🌐"),
            Category(2, "Data Science", "📊"),
            Category(3, "Business", "💼"),
            Category(4, "Design", "🎨"),
            Category(5, "Marketing", "📱"),
            Category(6, "IT and Software", "💻"),
            Category(7, "Personal Development", "🎯"),
            Category(8, "Photography", "📸")
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF000000)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Top Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFEA3035),
                                Color(0xFFEE631C)
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Expand Your Knowledge,\nElevate Your Career",
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Learn from industry experts and transform your skills with our cutting-edge courses.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFF9C27B0)
                            ),
                            modifier = Modifier.height(48.dp)
                        ) {
                            Text("Explore Courses",
                                color = Color(TextCyan.toArgb()))
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Featured Courses",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(courses) { course ->
                        CourseCard(course,navController)
                    }
                }
            }

            // Categories
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Top Categories",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.height(300.dp)
                ) {
                    items(categories) { category ->
                        CategoryCard(category)
                    }
                }
            }

            // Become Instructor CTA
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A237E))
                    .padding(32.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Become an instructor",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Join our community of expert instructors and share your knowledge with learners worldwide.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF1A237E)
                        )
                    ) {
                        Text("Start Teaching Today")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePagePreview() {
    val navController = rememberNavController()
    HomePage(navController)
}