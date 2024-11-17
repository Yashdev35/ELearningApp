package com.example.dbis_elearning_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.dbis_elearning_app.ui.theme.DBIS_eLearning_appTheme
import com.example.dbis_elearning_app.ui.theme.darkColorScheme
import com.example.dbis_elearning_app.ui_ux.UserScreens.AppNavigation
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation.StudentApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            //SignUpScreen()
            MaterialTheme(colorScheme = darkColorScheme) {
                window.navigationBarColor = Color(0xFF191819).toArgb()
                WindowCompat.setDecorFitsSystemWindows(window, false)
                AppNavigation(navController = navController)
                //StudentApp()
            }
        }

    }
}

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        DBIS_eLearning_appTheme {
            Greeting("Android")
        }
    }