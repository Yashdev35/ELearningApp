package com.example.dbis_elearning_app.ui.ui_ux.Student.StudentScreen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.dbis_elearning_app.PaymentActivity
import com.example.dbis_elearning_app.ui.theme.darkColorScheme

@Composable
fun CartScreen() {
val context = LocalContext.current
    var cartItems by remember { mutableStateOf(listOf(
        CartItem("The Complete Web Development Bootcamp", "Dr. Angela Yu", 3099.0, "https://placeholder.com/course1.jpg"),
        CartItem("Machine Learning A-Z™: Hands-On Python & R In Data Science", "Kirill Eremenko, Hadelin de Ponteves", 3499.0, "https://placeholder.com/course2.jpg")
    )) }
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    var recommendedCourses by remember { mutableStateOf(listOf(
        Course("Python for Data Science and Machine Learning Bootcamp", "Jose Portilla", 2999.0, 4.6f, "https://placeholder.com/course3.jpg"),
        Course("The Complete JavaScript Course 2023: From Zero to Expert!", "Jonas Schmedtmann", 3299.0, 4.7f, "https://placeholder.com/course4.jpg"),
        Course("React - The Complete Guide (incl Hooks, React Router, Redux)", "Maximilian Schwarzmüller", 3599.0, 4.8f, "https://placeholder.com/course5.jpg")
    )) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkColorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            "Your Cart",
            style = MaterialTheme.typography.headlineMedium,
            color = darkColorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth().
            height(screenHeight.times(0.25f))
        ) {
            items(cartItems) { item ->
                CartItemCard(item)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = darkColorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Total:",
                    style = MaterialTheme.typography.titleLarge,
                    color = darkColorScheme.onSurface
                )
                Text(
                    "₹${cartItems.sumOf { it.price }.toInt()}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = darkColorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val intent = Intent(context, PaymentActivity::class.java)
                        context.startActivity(intent)
                              },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = darkColorScheme.primary)
                ) {
                    Text("Checkout")
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            "Recommended Courses",
            style = MaterialTheme.typography.titleLarge,
            color = darkColorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            items(recommendedCourses) { course ->
                RecommendedCourseCard(course)
            }
        }
    }
}

@Composable
fun CartItemCard(item: CartItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = darkColorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(item.imageUrl),
                contentDescription = "Course Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    item.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = darkColorScheme.onSurface
                )
                Text(
                    item.instructor,
                    style = MaterialTheme.typography.bodyMedium,
                    color = darkColorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    "₹${item.price.toInt()}",
                    style = MaterialTheme.typography.titleMedium,
                    color = darkColorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun RecommendedCourseCard(course: Course) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = darkColorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(course.imageUrl),
                contentDescription = "Course Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    course.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = darkColorScheme.onSurface
                )
                Text(
                    course.instructor,
                    style = MaterialTheme.typography.bodyMedium,
                    color = darkColorScheme.onSurface.copy(alpha = 0.7f)
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
                    Text(
                        " ${course.rating}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = darkColorScheme.onSurface
                    )
                }
                Text(
                    "₹${course.price.toInt()}",
                    style = MaterialTheme.typography.titleMedium,
                    color = darkColorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

data class CartItem(
    val title: String,
    val instructor: String,
    val price: Double,
    val imageUrl: String
)

data class Course(
    val title: String,
    val instructor: String,
    val price: Double,
    val rating: Float,
    val imageUrl: String
)

@Preview
@Composable
fun CartScreenPreview() {
    CartScreen()
}