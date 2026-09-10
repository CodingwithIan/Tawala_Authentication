package com.example.dashboard.repo




import com.example.dashboard.utilis.ManifestWithParcels
import kotlinx.coroutines.flow.Flow

interface ManifestRepository {
    fun getActiveManifest(): Flow<ManifestWithParcels?>
    suspend fun startNewSession(plate: String, conductorName: String, conductorPhone: String): Long
    suspend fun completeSession(sessionId: Long)
}