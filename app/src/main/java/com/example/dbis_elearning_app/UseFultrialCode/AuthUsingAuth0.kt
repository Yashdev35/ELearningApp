package com.example.dbis_elearning_app.UseFultrialCode

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.dbis_elearning_app.data.model.Student

@Composable
fun UserScreen(
    student: Student?,
    onLogin: () -> Unit,
    onLogout: () -> Unit
) {
    if (student != null) {
        LoggedInScreen(student, onLogout)
        Log.d("UserScreen", student.idToken.toString())
    } else {
        LoginScreen(onLogin)
    }
}

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Please log in to continue")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLogin) {
            Text(text = "Login")
        }
    }
}

@Composable
fun LoggedInScreen(student: Student, onLogout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome, ${student.name}")
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = rememberImagePainter(student.picture),
            contentDescription = null,
            modifier = Modifier.size(100.dp).clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLogout) {
            Text(text = "Logout")
        }
    }
}

