package com.example.dashboard.data.dao



import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.dashboard.data.entity.ManifestSessionEntity
import com.example.dashboard.utilis.ManifestWithParcels
import kotlinx.coroutines.flow.Flow

@Dao
interface ManifestDao {

    @Transaction
    @Query("SELECT * FROM manifest_sessions WHERE isCompleted = 0 ORDER BY createdAt DESC LIMIT 1")
    fun getActiveManifestWithParcels(): Flow<ManifestWithParcels?>

    @Query("SELECT COUNT(*) FROM manifest_sessions WHERE isCompleted = 0")
    suspend fun getActiveSessionCount(): Int

    @Query("UPDATE manifest_sessions SET isCompleted = 1 WHERE isCompleted = 0")
    suspend fun closeAllActiveSessions()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: ManifestSessionEntity): Long

    @Transaction
    suspend fun startNewSessionSafely(session: ManifestSessionEntity): Long {
        closeAllActiveSessions()
        return insertSession(session)
    }

    @Query("UPDATE manifest_sessions SET isCompleted = 1 WHERE id = :sessionId")
    suspend fun markSessionComplete(sessionId: Long)
}