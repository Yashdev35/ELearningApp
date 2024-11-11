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
import com.example.dbis_elearning_app.data.model.User

@Composable
fun UserScreen(
    user: User?,
    onLogin: () -> Unit,
    onLogout: () -> Unit
) {
    if (user != null) {
        LoggedInScreen(user, onLogout)
        Log.d("UserScreen", user.idToken.toString())
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
fun LoggedInScreen(user: User, onLogout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome, ${user.name}")
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = rememberImagePainter(user.picture),
            contentDescription = null,
            modifier = Modifier.size(100.dp).clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLogout) {
            Text(text = "Logout")
        }
    }
}

