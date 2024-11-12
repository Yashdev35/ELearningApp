package com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.viewModel.StudentRegistrationViewModel
import com.example.dbis_elearning_app.ui.theme.TextCyan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentRegistrationStageTwo(
    viewModel: StudentRegistrationViewModel,
    onBack: () -> Unit,
    onSubmit: () -> Unit
) {
    var educationLevelExpanded by remember { mutableStateOf(false) }
    var yearExpanded by remember { mutableStateOf(false) }
    var specializationExpanded by remember { mutableStateOf(false) }

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
                text = "Education Information",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 24.dp)
            )

            // Education Level Dropdown
            ExposedDropdownMenuBox(
                expanded = educationLevelExpanded,
                onExpandedChange = { educationLevelExpanded = !educationLevelExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = viewModel.educationLevel,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Education Level") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = educationLevelExpanded) },
                    colors = TextFieldDefaults.colors(),
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = educationLevelExpanded,
                    onDismissRequest = { educationLevelExpanded = false },
                    modifier = Modifier.background(Color(0xFF1A1F2C))
                ) {
                    viewModel.educationLevelOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, color = Color.White) },
                            onClick = {
                                viewModel.educationLevel = option
                                viewModel.studyYear = ""
                                viewModel.specialization = ""
                                educationLevelExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Study Year Dropdown
            if (viewModel.educationLevel.isNotEmpty()) {
                ExposedDropdownMenuBox(
                    expanded = yearExpanded,
                    onExpandedChange = { yearExpanded = !yearExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = viewModel.studyYear,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Study Year") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = yearExpanded) },
                        colors = TextFieldDefaults.colors(),
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = yearExpanded,
                        onDismissRequest = { yearExpanded = false },
                        modifier = Modifier.background(Color(0xFF1A1F2C))
                    ) {
                        viewModel.getAvailableYears().forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, color = Color.White) },
                                onClick = {
                                    viewModel.studyYear = option
                                    yearExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Specialization Dropdown
                ExposedDropdownMenuBox(
                    expanded = specializationExpanded,
                    onExpandedChange = { specializationExpanded = !specializationExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = viewModel.specialization,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Specialization") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = specializationExpanded) },
                        colors = TextFieldDefaults.colors(),
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = specializationExpanded,
                        onDismissRequest = { specializationExpanded = false },
                        modifier = Modifier.background(Color(0xFF1A1F2C))
                    ) {
                        viewModel.getAvailableSpecializations().forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, color = Color.White) },
                                onClick = {
                                    viewModel.specialization = option
                                    specializationExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.linkedinProfile,
                onValueChange = { viewModel.linkedinProfile = it },
                label = { Text("LinkedIn Profile URL (Optional)") },
                singleLine = true,
                colors = TextFieldDefaults.colors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.githubLink,
                onValueChange = { viewModel.githubLink = it },
                label = { Text("GitHub Profile URL (Optional)") },
                singleLine = true,
                colors = TextFieldDefaults.colors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

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
                Text("Complete Registration",
                    color = Color(TextCyan.toArgb()))
            }
        }
    }
}

@Preview
@Composable
fun PreviewStudentRegistrationStageTwo() {
    StudentRegistrationStageTwo(
        viewModel = StudentRegistrationViewModel(),
        onBack = {},
        onSubmit = {}
    )
}