import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import com.arthenica.ffmpegkit.FFmpegKit
import com.example.dbis_elearning_app.viewModel.InstructorViewModel.InstructorVideoViewModel

@Composable
fun VideoUploadScreen(viewModel: InstructorVideoViewModel = hiltViewModel()) {
    var selectedVideoUri by remember { mutableStateOf<Uri?>(null) }
    var isConverting by remember { mutableStateOf(false) }
    var isUploading by remember { mutableStateOf(false) }
    var uploadSuccess by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val message = mutableStateOf<String?>(null)

    // Observing upload status
    viewModel.uploadStatus.value?.let {
        uploadSuccess = it
        isUploading = false
        message.value = viewModel.uploadStatus.value
        Toast.makeText(context, message.value, Toast.LENGTH_SHORT).show()
    }

    // Video picker launcher
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedVideoUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { launcher.launch("video/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pick Video")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                selectedVideoUri?.let { uri ->
                    isConverting = true
                    prepareVideoForUpload(context, uri) { videoPart ->
                        isConverting = false
                        isUploading = true
                        viewModel.uploadVideo(videoPart)
                    }
                } ?: Toast.makeText(context, "Please select a video first", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isConverting && !isUploading
        ) {
            Text(if (isUploading) "Uploading..." else "Upload Video")
        }

        if (isConverting) {
            Text("Converting video to MP4...", color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
        }

        uploadSuccess?.let {
            Text(
                text = message.value ?: "",
                color = if(message.value!!.contains("Upload successful!")) Color.Green else Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

private fun prepareVideoForUpload(context: Context, uri: Uri, onVideoReady: (MultipartBody.Part) -> Unit) {
    val realPath = getRealPathFromUri(context, uri)
    val outputVideoPath = "${context.externalCacheDir?.absolutePath}/converted_video.mp4"

    if (realPath?.endsWith(".mp4") == true) {
        val file = File(realPath)
        val requestBody = file.asRequestBody("video/mp4".toMediaTypeOrNull())
        // Use "video" as the field name, which is what Multer is expecting on the backend
        val videoPart = MultipartBody.Part.createFormData("video", file.name, requestBody)
        onVideoReady(videoPart)
    } else {
        val command = "-i $realPath -c:v libx264 -preset fast -c:a aac $outputVideoPath"

        FFmpegKit.executeAsync(command) { session ->
            if (session.returnCode.isSuccess) {
                val convertedFile = File(outputVideoPath)
                val requestBody = convertedFile.asRequestBody("video/mp4".toMediaTypeOrNull())
                val videoPart = MultipartBody.Part.createFormData("video", convertedFile.name, requestBody)
                onVideoReady(videoPart)
            } else {
                Toast.makeText(context, "Video conversion failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


private fun getRealPathFromUri(context: Context, uri: Uri): String? {
    var path: String? = null
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(MediaStore.Video.Media.DATA)
            path = cursor.getString(columnIndex)
        }
    }
    return path
}
