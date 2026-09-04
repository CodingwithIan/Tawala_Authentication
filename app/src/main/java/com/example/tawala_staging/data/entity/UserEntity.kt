package com.example.tawala_staging.data.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val badgeId: String,
    val fullName: String,
    val phoneNumber: String,
    val passwordHash: String
)

