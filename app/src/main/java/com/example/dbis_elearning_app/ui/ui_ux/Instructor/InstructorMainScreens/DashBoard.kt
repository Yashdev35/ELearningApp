package com.example.dbis_elearning_app.ui_ux.UserScreens.Instructor.InstructorScreen

import LoadingDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dbis_elearning_app.R
import com.example.dbis_elearning_app.data.student.model.EducationLevel
import com.example.dbis_elearning_app.ui.ui_ux.CommonComponents.ProfileSection
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.StudentScreen.BlackTransparent
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.StudentScreen.PureWhite
import com.example.dbis_elearning_app.viewModel.StudentViewModel.StudentApiViewModels.StudentDataViewModel
import kotlinx.coroutines.launch

data class Course(val name: String, val newStudents: Int, val totalHours: Int, val totalStudents: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructorDashboardScreen(
        userProfileImageRes: Int,
        userName: String,
        userEmail: String,
        viewModel: StudentDataViewModel = hiltViewModel(),
    ) {
    if(viewModel.isLoading.value){
        LoadingDialog()
    }
    val scope = rememberCoroutineScope()
    var isEditingEducation by remember { mutableStateOf(false) }
    var educationLevel by remember { mutableStateOf(EducationLevel()) }
    var isEditingProfile by remember { mutableStateOf(false) }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = BlackTransparent
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    ProfileSection(
                        profileImageRes = userProfileImageRes,
                        userName = userName,
                        userEmail = userEmail,
                        onEditProfile = { isEditingProfile = !isEditingProfile },
                        isEditProfile = isEditingProfile,
                        onProfileImageChanged = {}
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Text("Education Section", style = MaterialTheme.typography.titleMedium, color = PureWhite)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    EducationSection(
                        isEditing = isEditingEducation,
                        onEditClick = { isEditingEducation = !isEditingEducation
                                      educationLevel = EducationLevel() },
                        educationLevel = educationLevel,
                        onEducationLevelChange = { educationLevel = it },
                        onSaved = { scope.launch {
                           viewModel.updateEducation(educationLevel)
                        }}
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
        }
}
@Composable
fun EducationSection(
    isEditing: Boolean,
    onEditClick: () -> Unit,
    educationLevel: EducationLevel,
    onEducationLevelChange: (EducationLevel) -> Unit,
    onSaved: () -> Unit = {}
) {
    val level1Options = listOf("SCHOOL", "UNDERGRADUATE", "POSTGRADUATE")
    val level2OptionsMap = mapOf(
        "SCHOOL" to listOf("ELEVENTH", "TWELFTH"),
        "UNDERGRADUATE" to listOf("FIRST", "SECOND", "THIRD", "FOURTH"),
        "POSTGRADUATE" to listOf("FIRST", "SECOND")
    )
    val level3OptionsMap = mapOf(
        "ELEVENTH" to listOf("SCIENCE", "COMMERCE", "ARTS"),
        "TWELFTH" to listOf("SCIENCE", "COMMERCE", "ARTS"),
        "FIRST" to listOf("BTECH", "MTECH", "BSC", "MSC", "PHD"),
        "SECOND" to listOf("BTECH", "MTECH", "BSC", "MSC", "PHD"),
        "THIRD" to listOf("BTECH", "MTECH", "BSC", "MSC", "PHD"),
        "FOURTH" to listOf("BTECH", "MTECH", "BSC", "MSC", "PHD")
    )
    val level4OptionsMap = mapOf(
        "POSTGRADUATE" to listOf(
            "COMPUTER_SCIENCE",
            "MECHANICAL_ENGINEERING",
            "ELECTRICAL_ENGINEERING",
            "CIVIL_ENGINEERING",
            "CHEMISTRY",
            "PHYSICS"
        )
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Education",
                style = MaterialTheme.typography.titleMedium,
                color = PureWhite,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Education",
                    tint = PureWhite
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isEditing) {
            DropdownField(
                label = "Level 1",
                options = level1Options,
                selectedOption = educationLevel.level1,
                onOptionSelected = {
                    onEducationLevelChange(educationLevel.copy(level1 = it, level2 = "", level3 = ""))
                }
            )

            if (educationLevel.level1.isNotEmpty()) {
                DropdownField(
                    label = "Level 2",
                    options = level2OptionsMap[educationLevel.level1] ?: emptyList(),
                    selectedOption = educationLevel.level2,
                    onOptionSelected = {
                        onEducationLevelChange(educationLevel.copy(level2 = it, level3 = ""))
                    }
                )
            }

            if (educationLevel.level2.isNotEmpty()) {
                DropdownField(
                    label = "Level 3",
                    options = level3OptionsMap[educationLevel.level2] ?: emptyList(),
                    selectedOption = educationLevel.level3,
                    onOptionSelected = {
                        onEducationLevelChange(educationLevel.copy(level3 = it))
                    }
                )
            }
        } else {
            Column {
                Text(
                    text = "Level 1: ${educationLevel.level1.ifEmpty { "Not Set" }}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PureWhite
                )
                Text(
                    text = "Level 2: ${educationLevel.level2.ifEmpty { "Not Set" }}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PureWhite
                )
                Text(
                    text = "Level 3: ${educationLevel.level3.ifEmpty { "Not Set" }}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PureWhite
                )
            }
        }
    }
    if (isEditing && educationLevel.level1 == "POSTGRADUATE" && educationLevel.level3.isNotEmpty()) {
        DropdownField(
            label = "Specialization",
            options = level4OptionsMap["POSTGRADUATE"] ?: emptyList(),
            selectedOption = educationLevel.level4,
            onOptionSelected = {
                onEducationLevelChange(educationLevel.copy(level4 = it))
            }
        )
    }
    if(isEditing) {
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
               onSaved()
            },
            modifier = Modifier.wrapContentSize()
        ) {
            Text("Save")
        }
    }

}

@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label, color = Color.Black) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.White,
                disabledContainerColor = Color.White,
                disabledTextColor = PureWhite,
                focusedContainerColor = Color.White,
            ),
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand Dropdown",
                        tint = Color.Red
                    )
                }
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = Color.White,
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    text = { Text(option, color = Color.Black) }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewLearnHubDashboardScreen() {
    InstructorDashboardScreen(
        userProfileImageRes = R.drawable.dbis_project_logo_amber_removebg,
        userName = "",
        userEmail = "")
}