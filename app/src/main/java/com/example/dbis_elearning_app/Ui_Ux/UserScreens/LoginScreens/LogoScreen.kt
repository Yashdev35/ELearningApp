package com.example.dbis_elearning_app.Ui_Ux.UserScreens.LoginScreens
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dbis_elearning_app.R
import kotlinx.coroutines.delay

@Composable
fun LogoScreen(
    onTimeout: () -> Unit,
    isUserDataLoaded: () -> Boolean
) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    LaunchedEffect(Unit) {
        val startTime = System.currentTimeMillis()
        delay(3000) // Simulating a 3-second load time
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Pulsating circle
        Box(
            modifier = Modifier
                .size(200.dp)
                .scale(scale)
                .alpha(0.5f)
                .background(Color.White, CircleShape),
        )
        // Logo
        Image(
            painter = painterResource(id = R.drawable.learnhub_logo), // Replace with your logo resource
            contentDescription = "Logo",
            modifier = Modifier
                .size(100.dp) // Adjust size as needed
                .align(Alignment.Center),
            contentScale = ContentScale.Fit
        )
    }
}

@Preview
@Composable
fun LogoScreenPreview(){
    LogoScreen( onTimeout = {},isUserDataLoaded = { return@LogoScreen false})

}