package com.example.dashboard.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "manifest_sessions")
data class ManifestSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val vehiclePlate: String,
    val conductorName: String,
    val conductorPhone: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)