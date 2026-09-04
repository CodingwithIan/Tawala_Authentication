package com.example.tawala_staging.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tawala_staging.data.entity.SessionEntity

@Dao
interface SessionDao {
    @Query("SELECT * FROM active_session WHERE id = 1")
    suspend fun getActiveSession(): SessionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSession(session: SessionEntity)

    @Query("DELETE FROM active_session")
    suspend fun clearSession()
}