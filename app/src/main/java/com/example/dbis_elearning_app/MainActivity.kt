package com.example.dbis_elearning_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.dbis_elearning_app.UseFultrialCode.UserScreen
import com.example.dbis_elearning_app.ui.theme.DBIS_eLearning_appTheme
import com.example.dbis_elearning_app.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val authViewModel: AuthViewModel = hiltViewModel()
            val navController = rememberNavController()
            //AppNavigation(navController = navController)
            val user by remember { authViewModel.user }
            UserScreen(user = user, onLogin = { authViewModel.login(this) }) {
                authViewModel.logout(this)
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