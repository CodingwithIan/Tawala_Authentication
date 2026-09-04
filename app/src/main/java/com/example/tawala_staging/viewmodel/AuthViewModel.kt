package com.example.tawala_staging.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tawala_staging.data.AppDatabase
import com.example.tawala_staging.data.entity.UserEntity
import com.example.tawala_staging.data.repo.AuthRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface AuthState {
    object Idle : AuthState
    data class Success(val message: String) : AuthState
    data class Error(val message: String) : AuthState
}

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    // Initialize repository using the database DAOs
    private val database = AppDatabase.getDatabase(application)
    private val repository = AuthRepository(database.sessionDao(), database.userDao())

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun login(badgeId: String, pass: String) {
        if (badgeId.isBlank() || pass.isBlank()) {
            _authState.value = AuthState.Error("All fields are required")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val success = repository.login(badgeId.trim(), pass)
            if (success) {
                _authState.value = AuthState.Success("Welcome back!")
            } else {
                _authState.value = AuthState.Error("Invalid Badge ID or Password")
            }
        }
    }

    fun register(name: String, badgeId: String, phone: String, pass: String) {
        if (name.length < 3) {
            _authState.value = AuthState.Error("Name must be at least 3 characters")
            return
        }
        if (!badgeId.matches(Regex("^TAW-\\d{2,}$"))) {
            _authState.value = AuthState.Error("Invalid Badge format (e.g. TAW-01)")
            return
        }
        if (!phone.matches(Regex("^(07|01)\\d{8}$"))) {
            _authState.value = AuthState.Error("Enter valid Kenyan number (07XXXXXXXX)")
            return
        }
        if (pass.length < 6) {
            _authState.value = AuthState.Error("Password must be at least 6 characters")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newUser = UserEntity(badgeId.trim(), name.trim(), phone.trim(), pass)
                repository.register(newUser)
                _authState.value = AuthState.Success("Registration Successful! Please sign in.")
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Badge ID already exists")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}