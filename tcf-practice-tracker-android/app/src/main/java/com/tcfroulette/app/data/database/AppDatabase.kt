package com.tcfroulette.app.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private const val TAG = "AppDatabase"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Log.d(TAG, "Creating database instance from asset tcf.db")
                try {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "tcf.db"
                    )
                        .createFromAsset("tcf.db")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    Log.d(TAG, "Database instance created successfully")
                    instance
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to create database instance", e)
                    throw e
                }
            }
        }
    }
}
