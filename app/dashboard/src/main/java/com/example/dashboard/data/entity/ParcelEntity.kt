package com.example.dashboard.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "parcels",
    foreignKeys = [
        ForeignKey(
            entity = ManifestSessionEntity::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["sessionId"])]
)
data class ParcelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sessionId: Long,
    val recipientName: String,
    val recipientPhone: String,
    val destination: String,
    val fee: Double,
    val isDraft: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)