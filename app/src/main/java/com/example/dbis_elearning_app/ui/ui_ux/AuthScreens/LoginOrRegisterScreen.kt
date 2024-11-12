import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dbis_elearning_app.R
import com.example.dbis_elearning_app.data.model.User
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation.ScrRegisterScreen
import com.example.dbis_elearning_app.ui.theme.BackGroundBlack
import com.example.dbis_elearning_app.ui_ux.UserScreens.Student.Navigation.ScrAccountTypeSelector
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    user: User?,
    onLogin: () -> Unit,
    navController: NavController,
    backgroundColor: Color = Color.Black
) {
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(user) {
        if (user != null) {
            navController.navigate(ScrAccountTypeSelector(name = user.name))
        }
    }

    BoxWithAnimatedImage(backgroundColor) {
        Column(
            modifier = Modifier
                .padding(horizontal = 0.5.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color(BackGroundBlack.toArgb())
                )
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            if (user == null) {
                                isLoading = true
                                scope.launch {
                                    onLogin()
                                    delay(2000) // Simulating backend delay
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text("Get Started")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Click 'Get Started' to login/register",
                        color = Color.Cyan,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 30.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        if (isLoading) {
            LoadingDialog()
        }
    }
}

@Composable
fun LoadingDialog() {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp)
                .background(Color.Black, shape = RoundedCornerShape(16.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(80.dp),
                    color = Color.White,
                    strokeWidth = 8.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Loading...",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun RegisterScreen(navController: NavController, backgroundColor: Color = Color.Black) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    BoxWithAnimatedImage(backgroundColor) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    ,
                    colors = CardDefaults.cardColors(
                        containerColor = Color(BackGroundBlack.toArgb()),
                    )
            ){
                Column(
                    modifier = Modifier.padding(12.dp),
                ) {
                    Text(
                        "Register", style = MaterialTheme.typography.headlineLarge, color = Color.White
                        , textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name", color = Color.Gray) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = Color.White
                            )
                        },
                        colors = TextFieldDefaults.colors(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email", color = Color.Gray) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = null,
                                tint = Color.White
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = TextFieldDefaults.colors(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password", color = Color.Gray) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null,
                                tint = Color.White
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = TextFieldDefaults.colors(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { /* Handle registration */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text("Register")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = { navController.navigateUp() }) {
                        Text("Already have an account? Log in", color = Color.Cyan)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BoxWithAnimatedImage(
    backgroundColor: Color,
    content: @Composable BoxScope.() -> Unit
) {
    val imageResources = listOf(R.drawable.login_image1, R.drawable.chem_std, R.drawable.login_page_image1)
    var imageIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            imageIndex = (imageIndex + 1) % imageResources.size
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)) {
        AnimatedContent(
            targetState = imageResources[imageIndex],
            transitionSpec = {
                scaleIn(initialScale = 0.8f, animationSpec = tween(500)) +
                        fadeIn() with
                        fadeOut() + scaleOut(targetScale = 1.2f, animationSpec = tween(500))
            }, label = "Animation",
            modifier = Modifier
                .fillMaxWidth()
        ) { resId ->
            Image(
                painter = painterResource(id = resId),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 170.dp)
                    .width(300.dp)
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
        }
        content()
    }
}

@Composable
fun SocialLoginButton(
    text: String,
    icon: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .height(40.dp)
            .width(160.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Text(text, color = Color.Black)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    var user = User("")
    val navController = rememberNavController();
    LoginScreen(user = user,{}, navController =navController)
}