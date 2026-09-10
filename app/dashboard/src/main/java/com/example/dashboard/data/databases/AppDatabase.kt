package com.example.dashboard.data.databases



import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dashboard.data.dao.ManifestDao
import com.example.dashboard.data.entity.ManifestSessionEntity
import com.example.dashboard.data.entity.ParcelEntity


@Database(
    entities = [ManifestSessionEntity::class, ParcelEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun manifestDao(): ManifestDao
}