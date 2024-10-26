package com.example.dbis_elearning_app.Ui_Ux.UserScreens.Instructor.InstructorScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Content(val type: String, val value: String)
data class Subtopic(val name: String, val contents: List<Content>)
data class Chapter(val name: String, val description: String, val subtopics: List<Subtopic>)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadCourseScreen() {
    var courseName by remember { mutableStateOf("") }
    var chapters by remember { mutableStateOf(listOf<Chapter>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Upload Course") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = courseName,
                    onValueChange = { courseName = it },
                    label = { Text("Course Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(chapters) { chapter ->
                ChapterCard(
                    chapter = chapter,
                    onChapterChanged = { updatedChapter ->
                        chapters = chapters.map { if (it == chapter) updatedChapter else it }
                    },
                    onDeleteChapter = {
                        chapters = chapters.filter { it != chapter }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Button(
                    onClick = {
                        chapters = chapters + Chapter("New Chapter", "", emptyList())
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Chapter")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Chapter")
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Handle course upload */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Upload Course")
                }
            }
        }
    }
}

@Composable
fun ChapterCard(
    chapter: Chapter,
    onChapterChanged: (Chapter) -> Unit,
    onDeleteChapter: () -> Unit
) {
    var chapterName by remember { mutableStateOf(chapter.name) }
    var chapterDescription by remember { mutableStateOf(chapter.description) }
    var subtopics by remember { mutableStateOf(chapter.subtopics) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Chapter",
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = onDeleteChapter) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Chapter")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = chapterName,
                onValueChange = {
                    chapterName = it
                    onChapterChanged(chapter.copy(name = it))
                },
                label = { Text("Chapter Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = chapterDescription,
                onValueChange = {
                    chapterDescription = it
                    onChapterChanged(chapter.copy(description = it))
                },
                label = { Text("Chapter Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            subtopics.forEachIndexed { index, subtopic ->
                SubtopicCard(
                    subtopic = subtopic,
                    onSubtopicChanged = { updatedSubtopic ->
                        subtopics = subtopics.toMutableList().also { it[index] = updatedSubtopic }
                        onChapterChanged(chapter.copy(subtopics = subtopics))
                    },
                    onDeleteSubtopic = {
                        subtopics = subtopics.filterIndexed { i, _ -> i != index }
                        onChapterChanged(chapter.copy(subtopics = subtopics))
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Button(
                onClick = {
                    subtopics = subtopics + Subtopic("New Subtopic", emptyList())
                    onChapterChanged(chapter.copy(subtopics = subtopics))
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Subtopic")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Subtopic")
            }
        }
    }
}

@Composable
fun SubtopicCard(
    subtopic: Subtopic,
    onSubtopicChanged: (Subtopic) -> Unit,
    onDeleteSubtopic: () -> Unit
) {
    var subtopicName by remember { mutableStateOf(subtopic.name) }
    var contents by remember { mutableStateOf(subtopic.contents) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Subtopic",
                    style = MaterialTheme.typography.titleSmall
                )
                IconButton(onClick = onDeleteSubtopic) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Subtopic")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = subtopicName,
                onValueChange = {
                    subtopicName = it
                    onSubtopicChanged(subtopic.copy(name = it))
                },
                label = { Text("Subtopic Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            contents.forEachIndexed { index, content ->
                ContentItem(
                    content = content,
                    onContentChanged = { updatedContent ->
                        contents = contents.toMutableList().also { it[index] = updatedContent }
                        onSubtopicChanged(subtopic.copy(contents = contents))
                    },
                    onDeleteContent = {
                        contents = contents.filterIndexed { i, _ -> i != index }
                        onSubtopicChanged(subtopic.copy(contents = contents))
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Button(
                onClick = {
                    contents = contents + Content("", "")
                    onSubtopicChanged(subtopic.copy(contents = contents))
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Content")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Content")
            }
        }
    }
}

@Composable
fun ContentItem(
    content: Content,
    onContentChanged: (Content) -> Unit,
    onDeleteContent: () -> Unit
) {
    var contentType by remember { mutableStateOf(content.type) }
    var contentValue by remember { mutableStateOf(content.value) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = contentType,
            onValueChange = {
                contentType = it
                onContentChanged(content.copy(type = it))
            },
            label = { Text("Type") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        OutlinedTextField(
            value = contentValue,
            onValueChange = {
                contentValue = it
                onContentChanged(content.copy(value = it))
            },
            label = { Text("Value") },
            modifier = Modifier.weight(2f)
        )
        IconButton(onClick = onDeleteContent) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Content")
        }
    }
}
@Preview
@Composable
fun UploadCourseScreenPreview() {
    UploadCourseScreen()
}