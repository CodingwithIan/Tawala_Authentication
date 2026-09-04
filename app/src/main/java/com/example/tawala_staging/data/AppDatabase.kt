package com.example.tawala_staging.data



import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tawala_staging.data.dao.SessionDao
import com.example.tawala_staging.data.dao.UserDao
import com.example.tawala_staging.data.entity.SessionEntity
import com.example.tawala_staging.data.entity.UserEntity


@Database(entities = [UserEntity::class, SessionEntity::class], version = 2, exportSchema = false)abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun sessionDao(): SessionDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tawala_sacco_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}