package com.example.dbis_elearning_app

import LoadingDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dbis_elearning_app.viewModel.StudentViewModel.StudentSignUpViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    userViewModel: StudentSignUpViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    if (userViewModel.isLogin.value) {
       LoadingDialog()
    }
    val signUpResult by remember { mutableStateOf(userViewModel.signUpResult.value) }

    // Define UI elements (TextFields for input and a Sign-Up button)
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Button(onClick = {
            scope.launch {
                userViewModel.signUpUser(name, email, password)
            }
        }) {
            Text("Sign Up")
        }

        signUpResult?.let { response ->
            if (response.statusCode == 201) {
                Text("User created successfully: ${response.data?.name}")
            } else {
                Text("Error: ${response.message}")
            }
        }
    }
}

@Preview
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen()
}
