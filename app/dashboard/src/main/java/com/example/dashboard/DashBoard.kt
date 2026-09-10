package com.example.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.room.Room
import com.example.dashboard.data.databases.AppDatabase
import com.example.dashboard.repo.ManifestRepositoryImpl
import com.example.dashboard.ui.components.DashboardRoute
import com.example.dashboard.viewmodel.DashboardViewModel
import com.example.dashboard.viewmodel.DashboardViewModelFactory

class DashBoard : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Initialize Room Database & DAO
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "tawala_parcel_db"
        ).build()

        val manifestDao = db.manifestDao()

        // 2. Initialize Repository & ViewModel
        val repository = ManifestRepositoryImpl(manifestDao)
        val viewModel: DashboardViewModel by viewModels {
            DashboardViewModelFactory(repository)
        }

        // 3. Render the Composables
        setContent {
            // Apply your theme wrapper here if available
            DashboardRoute(
                viewModel = viewModel,
                onNavigateToAddParcel = { sessionId ->
                    // Navigate to Add Parcel Screen using sessionId
                }
            )
        }
    }
}