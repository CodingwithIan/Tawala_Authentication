package com.example.tawala_staging.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tawala_staging.viewmodel.AuthViewModel
import com.example.tawala_staging.viewmodel.AuthState

// --- Exact Palette Constants from Your Design Spec ---
private val DeepMidnightNavy = Color(0xFF1B2A58) // Main background & sidebars
private val VibrantMagenta = Color(0xFFE82C89)   // Buttons, active tabs & accents
private val SoftPinkTint = Color(0xFFF7DCED)     // Labels & secondary text
private val PureWhite = Color(0xFFFFFFFF)        // Card containers & overlays
private val JetBlack = Color(0xFF000000)         // Primary text on white backgrounds

@Composable
fun AuthScreen(viewModel: AuthViewModel, onLoginSuccess: () -> Unit) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Login, 1: Register

    var loginBadge by remember { mutableStateOf("TAW-MARSHAL-01") }
    var loginPass by remember { mutableStateOf("") }

    var regName by remember { mutableStateOf("") }
    var regBadge by remember { mutableStateOf("") }
    var regPhone by remember { mutableStateOf("") }
    var regPass by remember { mutableStateOf("") }

    val authState by viewModel.authState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthState.Success -> {
                snackbarHostState.showSnackbar(state.message)
                if (selectedTab == 1 && state.message.contains("Successful")) {
                    selectedTab = 0
                } else if (selectedTab == 0 && state.message.contains("Welcome")) {
                    onLoginSuccess()
                }
                viewModel.resetState()
            }
            is AuthState.Error -> {
                snackbarHostState.showSnackbar(state.message)
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = DeepMidnightNavy
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .width(360.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(DeepMidnightNavy, CircleShape)
                            .border(2.dp, VibrantMagenta, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("T", fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("TAWALA SACCO", color = DeepMidnightNavy, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Parcel Tracking & Stage System", color = JetBlack, fontSize = 10.sp)

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SoftPinkTint, RoundedCornerShape(8.dp))
                            .padding(4.dp)
                    ) {
                        TabButton(text = "Sign In", selected = selectedTab == 0) { selectedTab = 0 }
                        TabButton(text = "Register", selected = selectedTab == 1) { selectedTab = 1 }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (selectedTab == 0) {
                        Text("Marshal Sign In", color = JetBlack, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Enter credentials to open stage session", color = DeepMidnightNavy, fontSize = 10.sp)
                        Spacer(modifier = Modifier.height(12.dp))

                        AuthTextField(value = loginBadge, label = "Marshal Badge ID") { loginBadge = it }
                        AuthTextField(value = loginPass, label = "Password", isPassword = true) { loginPass = it }

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.login(loginBadge, loginPass) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = VibrantMagenta),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("LOG IN TO STAGE", color = PureWhite, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Text("Marshal Registration", color = JetBlack, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Create a new badge account", color = DeepMidnightNavy, fontSize = 10.sp)
                        Spacer(modifier = Modifier.height(12.dp))

                        AuthTextField(value = regName, label = "Full Name") { regName = it }
                        AuthTextField(value = regBadge, label = "Marshal Badge ID (TAW-MARSHAL-XX)") { regBadge = it }
                        AuthTextField(value = regPhone, label = "Phone Number (07XXXXXXXX)", keyboardType = KeyboardType.Phone) { regPhone = it }
                        AuthTextField(value = regPass, label = "Password", isPassword = true) { regPass = it }

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.register(regName, regBadge, regPhone, regPass) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = VibrantMagenta),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("REGISTER BADGE", color = PureWhite, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.TabButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .height(36.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) VibrantMagenta else Color.Transparent,
            contentColor = if (selected) PureWhite else DeepMidnightNavy
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(text, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun AuthTextField(
    value: String,
    label: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Text(label, color = DeepMidnightNavy, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = JetBlack,
                unfocusedTextColor = JetBlack,
                focusedContainerColor = PureWhite,
                unfocusedContainerColor = PureWhite,
                cursorColor = VibrantMagenta,
                focusedBorderColor = VibrantMagenta,
                unfocusedBorderColor = DeepMidnightNavy
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true
        )
    }
}