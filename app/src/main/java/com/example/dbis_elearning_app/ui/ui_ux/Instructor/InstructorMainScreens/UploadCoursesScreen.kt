import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dbis_elearning_app.viewModel.InstructorViewModel.CourseUploadViewModel

data class VideoContent(
    var uri: Uri? = null,
    var description: String = ""
)

data class Subtopic(
    var name: String = "",
    var video: VideoContent = VideoContent()
)

data class Chapter(
    var name: String = "",
    var subtopics: MutableList<Subtopic> = mutableStateListOf()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseUploadScreen(viewModel: CourseUploadViewModel) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.courseImageUri.value = uri
    }

    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.promoVideoUri.value = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            "Create Course",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = viewModel.courseTitle.value,
            onValueChange = { viewModel.courseTitle.value = it },
            label = { Text("Course Title") },
            colors = TextFieldDefaults.colors(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable { imagePickerLauncher.launch("image/*") },
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFF1E1E1E)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.Image,
                    contentDescription = "Upload Image",
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    if (viewModel.courseImageUri.value == null) "Upload Course Image" else "Image Selected",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable { videoPickerLauncher.launch("video/*") },
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFF1E1E1E)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.VideoLibrary,
                    contentDescription = "Upload Promo Video",
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    if (viewModel.promoVideoUri.value == null) "Upload Promo Video" else "Video Selected",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        viewModel.chapters.forEachIndexed { chapterIndex, chapter ->
            ChapterSection(
                chapter = chapter,
                onChapterNameChange = { viewModel.updateChapterName(chapterIndex, it) },
                onDeleteChapter = { viewModel.deleteChapter(chapterIndex) },
                onAddSubtopic = { viewModel.addSubtopic(chapterIndex) },
                onUpdateSubtopic = { subtopicIndex, updatedSubtopic ->
                    viewModel.updateSubtopic(chapterIndex, subtopicIndex, updatedSubtopic)
                },
                onDeleteSubtopic = { subtopicIndex ->
                    viewModel.deleteSubtopic(chapterIndex, subtopicIndex)
                }
            )
        }

        // Add Chapter Button
        Button(
            onClick = { viewModel.addChapter() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
        ) {
            Icon(Icons.Default.Add, "Add Chapter")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Chapter")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Submit Button
        Button(
            onClick = { /* Handle course upload with ViewModel data */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text("Upload Course")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterSection(
    chapter: Chapter,
    onChapterNameChange: (String) -> Unit,
    onDeleteChapter: () -> Unit,
    onAddSubtopic: () -> Unit,
    onUpdateSubtopic: (Int, Subtopic) -> Unit,
    onDeleteSubtopic: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = chapter.name,
                    onValueChange = onChapterNameChange,
                    label = { Text("Chapter Name") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors()
                )
                IconButton(onClick = onDeleteChapter) {
                    Icon(Icons.Default.Delete, "Delete Chapter", tint = Color.Red)
                }
            }

            // Subtopics
            chapter.subtopics.forEachIndexed { index, subtopic ->
                SubtopicSection(
                    subtopic = subtopic,
                    onUpdateSubtopic = { onUpdateSubtopic(index, it) },
                    onDeleteSubtopic = { onDeleteSubtopic(index) }
                )
            }

            // Add Subtopic Button
            TextButton(
                onClick = onAddSubtopic,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, "Add Subtopic")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Subtopic")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubtopicSection(
    subtopic: Subtopic,
    onUpdateSubtopic: (Subtopic) -> Unit,
    onDeleteSubtopic: () -> Unit
) {
    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onUpdateSubtopic(subtopic.copy(video = subtopic.video.copy(uri = uri)))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF262626)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = subtopic.name,
                    onValueChange = { onUpdateSubtopic(subtopic.copy(name = it)) },
                    label = { Text("Subtopic Name") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors()
                )
                IconButton(onClick = onDeleteSubtopic) {
                    Icon(Icons.Default.Close, "Delete Subtopic", tint = Color.Red)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Video Upload Button
            Button(
                onClick = { videoPickerLauncher.launch("video/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.VideoLibrary, "Upload Video")
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (subtopic.video.uri == null) "Upload Video" else "Video Selected")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Video Description
            OutlinedTextField(
                value = subtopic.video.description,
                onValueChange = {
                    onUpdateSubtopic(subtopic.copy(
                        video = subtopic.video.copy(description = it)
                    ))
                },
                label = { Text("Video Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = TextFieldDefaults.colors()
            )
        }
    }
}

@Preview
@Composable
fun CourseUploadScreenPreview() {
    val courseUploadViewModel : CourseUploadViewModel = viewModel()
    CourseUploadScreen(courseUploadViewModel)
}