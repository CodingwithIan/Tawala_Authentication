package com.example.tawala_staging.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "active_session")
data class SessionEntity(
    @PrimaryKey
    val id: Int = 1, // Fixed ID to enforce single-row session storage
    val badgeId: String,
    val fullName: String,
    val phoneNumber: String
)