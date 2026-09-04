package com.example.tawala_staging

// Ensure this matches your package name exactly

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tawala_staging.ui.auth.AuthScreen
import com.example.tawala_staging.ui.theme.Tawala_StagingTheme
import com.example.tawala_staging.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // This applies your app's base theme
            Tawala_StagingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 1. Initialize the ViewModel
                    val authViewModel: AuthViewModel = viewModel()

                    // 2. Call your custom AuthScreen
                    AuthScreen(
                        viewModel = authViewModel,
                        onLoginSuccess = {
                            // For now, just show a toast. Later we will add Navigation here.
                            Toast.makeText(
                                this@MainActivity,
                                "Login Success! Routing to Dashboard...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
}