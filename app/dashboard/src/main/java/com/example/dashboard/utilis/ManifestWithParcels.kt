package com.example.dashboard.utilis

import androidx.room.Embedded
import androidx.room.Relation
import com.example.dashboard.data.entity.ManifestSessionEntity
import com.example.dashboard.data.entity.ParcelEntity


data class ManifestWithParcels(
    @Embedded val session: ManifestSessionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "sessionId"
    )
    val parcels: List<ParcelEntity>
)