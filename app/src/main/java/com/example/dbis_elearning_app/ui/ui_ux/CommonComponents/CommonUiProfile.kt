package com.example.dbis_elearning_app.ui.ui_ux.CommonComponents

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.dbis_elearning_app.R


@Composable
fun ProfileSection(
    profileImageRes: Int,
    userName: String,
    userEmail: String,
    onEditProfile: () -> Unit,
    isEditProfile: Boolean = false,
    onProfileImageChanged: (Uri) -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            onProfileImageChanged(it)
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .clickable(enabled = isEditProfile) {
                    if (isEditProfile) {
                        launcher.launch("image/*")
                    }
                }
        ) {
            Image(
                painter = if (imageUri != null) {
                    rememberAsyncImagePainter(
                        ImageRequest.Builder(context).data(data = imageUri).build()
                    )
                } else {
                    painterResource(id = profileImageRes)
                },
                contentDescription = "Profile Picture",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            if (isEditProfile) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x80000000))
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Profile Picture",
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(userName, style = MaterialTheme.typography.titleLarge, color = Color.White)
        Text(userEmail, style = MaterialTheme.typography.bodyMedium, color = Color.LightGray)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onEditProfile,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xA9000000)),
            shape = RoundedCornerShape(30.dp)
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Edit Profile", tint = Color.White)
        }
    }
}

@Preview
@Composable
fun PreviewProfileSection() {
    ProfileSection(
        profileImageRes = R.drawable.dbis_project_logo_amber_removebg,
        userName = "John Doe",
        userEmail = "",
        onEditProfile = {},
        isEditProfile = false,
        onProfileImageChanged = {})
}