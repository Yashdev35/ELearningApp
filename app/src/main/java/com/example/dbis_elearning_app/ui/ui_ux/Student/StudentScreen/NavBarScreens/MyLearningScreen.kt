package com.example.dbis_elearning_app.ui.ui_ux.Student.StudentScreen.NavBarScreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dbis_elearning_app.R
import com.example.dbis_elearning_app.ui.theme.darkColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLearningScreen() {
    MaterialTheme(colorScheme = darkColorScheme) {
        var selectedFilter by remember { mutableStateOf("All") }
        val filters = listOf("All", "Downloaded", "Favorites")

        val allCours = listOf(
            CourseMyLearning(
                title = "Web Development Bootcamp",
                instructor = "John Doe",
                progress = "75% complete",
                thumbnailUrl = stringResource(id = R.string.courseImage1),
                isDownloaded = false,
                isFavorite = true
            ),
            CourseMyLearning(
                title = "Machine Learning A-Z",
                instructor = "Jane Smith",
                progress = "30% complete",
                thumbnailUrl = stringResource(id = R.string.courseImage2),
                isDownloaded = true,
                isFavorite = false
            ),
            CourseMyLearning(
                title = "iOS App Development",
                instructor = "Tim Cook",
                progress = "50% complete",
                thumbnailUrl = stringResource(id = R.string.courseImage3),
                isDownloaded = true,
                isFavorite = false
            ),
            CourseMyLearning(
                title = "Python for Data Science",
                instructor = "Ana Johnson",
                progress = "10% complete",
                thumbnailUrl = stringResource(id = R.string.courseImage4),
                isDownloaded = false,
                isFavorite = true
            )
        )

        val filteredCourses = remember(selectedFilter) {
            when (selectedFilter) {
                "Downloaded" -> allCours.filter { it.isDownloaded }
                "Favorites" -> allCours.filter { it.isFavorite }
                else -> allCours
            }
        }

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopAppBar(
                    title = { Text("My Learning", color = MaterialTheme.colorScheme.onBackground) },
                    colors = TopAppBarDefaults.topAppBarColors()
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Filter chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(filters) { filter ->
                        FilterChip(
                            selected = filter == selectedFilter,
                            onClick = { selectedFilter = filter },
                            label = { Text(filter) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }

                // Course list
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredCourses) { course ->
                        MyLearningHorizontalCourseCard(course)
                    }
                }
            }
        }
    }
}

@Composable
fun MyLearningHorizontalCourseCard(courseMyLearning: CourseMyLearning) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // Thumbnail
            AsyncImage(
                model = courseMyLearning.thumbnailUrl,
                contentDescription = "Course thumbnail",
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )

            // Course details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Text(
                    text = courseMyLearning.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = courseMyLearning.instructor,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = courseMyLearning.progress.removeSuffix("% complete").toFloat() / 100f,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = courseMyLearning.progress,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}

data class CourseMyLearning(
    val title: String,
    val instructor: String,
    val progress: String,
    val thumbnailUrl: String,
    val isDownloaded: Boolean = false,
    val isFavorite: Boolean = false
)

@Preview
@Composable
fun MyLearningScreenPreview() {
    MyLearningScreen()
}
