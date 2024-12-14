package com.example.dbis_elearning_app.ui.ui_ux.Student.StudentScreen.NavBarScreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    val allCourses = listOf(
        "Android Development with Kotlin",
        "Advanced Machine Learning",
        "Web Development with React",
        "Backend Development with Spring Boot",
        "Frontend Development with Angular",
        "Mobile Development with Flutter",
        "DevOps and CI/CD",
        "Data Analytics with Python",
        "Artificial Intelligence Basics",
        "Cyber Security Fundamentals",
        "Project Management Professional",
        "Machine Learning for Beginners",
        "AI for Everyone Course",
        "Google Data Analytics",
        "Digital Marketing Basics",
        "Psychology 101",
        "Power BI for Data Analysis",
        "Google Cloud Platform Basics"
    )

    // Filter courses based on search query
    val filteredCourses = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            emptyList()
        } else {
            allCourses.filter {
                it.contains(searchQuery, ignoreCase = true)
            }.take(5) // Limit to 5 suggestions
        }
    }

    val popularTags = listOf(
        "Machine Learning",
        "Web Development",
        "Backend",
        "Frontend",
        "Mobile Development",
        "DevOps"
    )

    val popularSearches = listOf(
        "ai",
        "data analytics",
        "cyber security",
        "project management",
        "machine learning",
        "ai for everyone",
        "google data analytics professional certificate",
        "digital marketing",
        "psychology",
        "power bi",
        "google"
    )

    Scaffold(
        containerColor = Color(0xFF121212),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF121212))
                .padding(16.dp)
        ) {
            Text(
                "Search",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { },
                active = isSearchActive,
                onActiveChange = { isSearchActive = it },
                placeholder = { Text("What do you want to learn?") },
                modifier = Modifier.fillMaxWidth(),
                colors = SearchBarDefaults.colors(
                    containerColor = Color(0xFF1E1E1E),
                    inputFieldColors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray
                    )
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = Color.White
                            )
                        }
                    }
                }
            ) {
                // Search suggestions
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1E1E1E))
                ) {
                    items(filteredCourses) { course ->
                        SearchSuggestionItem(
                            suggestion = course,
                            searchQuery = searchQuery,
                            onSuggestionClick = {
                                searchQuery = course
                                isSearchActive = false
                            }
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = !isSearchActive,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Popular Tags",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        items(popularTags) { tag ->
                            AssistChip(
                                onClick = {
                                    searchQuery = tag
                                    isSearchActive = true
                                },
                                label = { Text(tag) },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = Color(0xFF1E1E1E),
                                    labelColor = Color.White
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Popular Searches",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(popularSearches) { search ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        searchQuery = search
                                        isSearchActive = true
                                    }
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.TrendingFlat,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = search,
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchSuggestionItem(
    suggestion: String,
    searchQuery: String,
    onSuggestionClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSuggestionClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Highlight matching text
        val startIndex = suggestion.indexOf(searchQuery, ignoreCase = true)
        if (startIndex != -1) {
            val beforeMatch = suggestion.substring(0, startIndex)
            val match = suggestion.substring(startIndex, startIndex + searchQuery.length)
            val afterMatch = suggestion.substring(startIndex + searchQuery.length)

            Row {
                if (beforeMatch.isNotEmpty()) {
                    Text(
                        text = beforeMatch,
                        color = Color.White
                    )
                }
                Text(
                    text = match,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                if (afterMatch.isNotEmpty()) {
                    Text(
                        text = afterMatch,
                        color = Color.White
                    )
                }
            }
        } else {
            Text(
                text = suggestion,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}