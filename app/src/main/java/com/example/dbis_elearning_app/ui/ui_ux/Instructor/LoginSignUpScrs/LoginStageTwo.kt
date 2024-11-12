package com.example.dbis_elearning_app.ui_ux.UserScreens.Instructor.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dbis_elearning_app.R
import com.example.dbis_elearning_app.ui_ux.UserScreens.Instructor.viewModel.InstructorRegistrationViewModel
import com.example.dbis_elearning_app.ui.theme.TextCyan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructorRegistrationStageTwo(
    viewModel: InstructorRegistrationViewModel,
    onBack: () -> Unit,
    onSubmit: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF0A0E17)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top Bar
            IconButton(
                onClick = onBack,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF62DAFB)
                )
            }

            Text(
                text = "Professional Information",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 24.dp)
            )

            // Form Fields
            OutlinedTextField(
                value = viewModel.contactNumber,
                onValueChange = { viewModel.contactNumber = it },
                label = { Text("Contact Number") },
                singleLine = true,
                colors = TextFieldDefaults.colors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Qualification Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = viewModel.qualification,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Qualification") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = TextFieldDefaults.colors(),
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color(0xFF1A1F2C))
                ) {
                    viewModel.qualificationOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, color = Color.White) },
                            onClick = {
                                viewModel.qualification = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.linkedinProfile,
                onValueChange = { viewModel.linkedinProfile = it },
                label = { Text("LinkedIn Profile URL") },
                singleLine = true,
                colors = TextFieldDefaults.colors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.githubLink,
                onValueChange = { viewModel.githubLink = it },
                label = { Text("GitHub Profile URL") },
                singleLine = true,
                colors = TextFieldDefaults.colors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.achievements,
                onValueChange = { viewModel.achievements = it },
                label = { Text("Achievements") },
                minLines = 3,
                maxLines = 5,
                colors = TextFieldDefaults.colors(),
                modifier = Modifier.fillMaxWidth()
            )



            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.learnhub_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(100.dp),
                contentScale = ContentScale.Fit
            )
            Button(
                onClick = onSubmit,
                enabled = viewModel.isStageTwoValid(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF62DAFB),
                    contentColor = Color.Black
                )
            ) {
                Text("Complete Registration", color = Color(TextCyan.toArgb()))
            }
        }
    }
}
@Preview
@Composable
fun PreviewInstructorRegistrationStageTwo() {
    InstructorRegistrationStageTwo(
        viewModel = InstructorRegistrationViewModel(),
        onBack = {},
        onSubmit = {}
    )
}