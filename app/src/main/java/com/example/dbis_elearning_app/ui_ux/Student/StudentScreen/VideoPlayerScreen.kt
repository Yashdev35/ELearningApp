package com.example.dbis_elearning_app.ui_ux.UserScreens.Student.StudentScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedSection by remember { mutableStateOf("Introduction to React") }
    var comment by remember { mutableStateOf("") }
    var personalNote by remember { mutableStateOf("") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Course Sections", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
                Divider()
                CourseSections(
                    sections = listOf("Introduction to React", "React Hooks", "State Management"),
                    selectedSection = selectedSection,
                    onSectionSelected = { selectedSection = it }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("LearnHub") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFF1A202C))
            ) {
                item { VideoPlayer() }
                item { VideoControls() }
                item { VideoTitle() }
                item { LikeDislikeButtons() }
                item { CommentSection(comment, onCommentChange = { comment = it }) }
                item { PersonalNotes(personalNote, onNoteChange = { personalNote = it }) }
            }
        }
    }
}

@Composable
fun CourseSections(
    sections: List<String>,
    selectedSection: String,
    onSectionSelected: (String) -> Unit
) {
    sections.forEach { section ->
        ListItem(
            headlineContent = { Text(section) },
            leadingContent = {
                if (section == selectedSection) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Selected")
                }
            },
            modifier = Modifier.clickable { onSectionSelected(section) }
        )
    }
}

@Composable
fun VideoPlayer() {
    // Placeholder for actual video player
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Default.PlayCircle,
            contentDescription = "Play",
            tint = Color.White,
            modifier = Modifier.size(64.dp)
        )
    }
}

@Composable
fun VideoControls() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            IconButton(onClick = { /* Handle rewind */ }) {
                Icon(Icons.Default.SkipPrevious, contentDescription = "Rewind", tint = Color.White)
            }
            IconButton(onClick = { /* Handle play/pause */ }) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Play/Pause", tint = Color.White)
            }
            IconButton(onClick = { /* Handle forward */ }) {
                Icon(Icons.Default.SkipNext, contentDescription = "Forward", tint = Color.White)
            }
        }
        Text("00:00 / 00:00", color = Color.White)
    }
    LinearProgressIndicator(
        progress = 0.3f,
        modifier = Modifier.fillMaxWidth(),
        color = Color.Red
    )
}

@Composable
fun VideoTitle() {
    Text(
        "Current Video Title",
        color = Color.White,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun LikeDislikeButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            onClick = { /* Handle like */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Icon(Icons.Default.ThumbUp, contentDescription = "Like", tint = Color.White)
            Spacer(Modifier.width(4.dp))
            Text("Like", color = Color.White)
        }
        Spacer(Modifier.width(8.dp))
        Button(
            onClick = { /* Handle dislike */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Icon(Icons.Default.ThumbDown, contentDescription = "Dislike", tint = Color.White)
            Spacer(Modifier.width(4.dp))
            Text("Dislike", color = Color.White)
        }
    }
}

@Composable
fun CommentSection(comment: String, onCommentChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Comments", color = Color.White, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        TextField(
            value = comment,
            onValueChange = onCommentChange,
            placeholder = { Text("Add a comment...") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { /* Handle comment submission */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Comment")
        }
        // Display existing comments here
        ExistingComment(
            author = "John Doe",
            content = "Great video!",
            timestamp = "5/17/2021, 12:26:07 PM"
        )
    }
}

@Composable
fun ExistingComment(author: String, content: String, timestamp: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(author, color = Color.White, fontWeight = FontWeight.Bold)
        Text(content, color = Color.White)
        Text(timestamp, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun PersonalNotes(note: String, onNoteChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Personal Notes", color = Color.White, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        TextField(
            value = note,
            onValueChange = onNoteChange,
            placeholder = { Text("Add a note...") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { /* Handle note submission */ }) {
                Text("Add Note")
            }
            Button(onClick = { /* Handle adding timestamp */ }) {
                Text("Add with Timestamp")
            }
        }
    }
}

@Preview
@Composable
fun PreviewVideoPlayerScreen() {
    VideoPlayerScreen()
}