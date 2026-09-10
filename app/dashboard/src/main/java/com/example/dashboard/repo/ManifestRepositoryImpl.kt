package com.example.dashboard.repo


import com.example.dashboard.data.dao.ManifestDao
import com.example.dashboard.data.entity.ManifestSessionEntity
import com.example.dashboard.utilis.ManifestWithParcels
import kotlinx.coroutines.flow.Flow

class ManifestRepositoryImpl(
    private val dao: ManifestDao
) : ManifestRepository {

    override fun getActiveManifest(): Flow<ManifestWithParcels?> =
        dao.getActiveManifestWithParcels()

    override suspend fun startNewSession(plate: String, conductorName: String, conductorPhone: String): Long {
        val sanitizedPlate = plate.trim().uppercase()
        val sanitizedName = conductorName.trim()
        val sanitizedPhone = conductorPhone.trim()

        val session = ManifestSessionEntity(
            vehiclePlate = sanitizedPlate,
            conductorName = sanitizedName,
            conductorPhone = sanitizedPhone
        )
        return dao.startNewSessionSafely(session)
    }

    override suspend fun completeSession(sessionId: Long) {
        dao.markSessionComplete(sessionId)
    }
}