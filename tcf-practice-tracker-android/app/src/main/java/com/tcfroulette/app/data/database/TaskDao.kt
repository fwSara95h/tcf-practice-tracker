package com.tcfroulette.app.data.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomTask(): TaskEntity?

    @Query("SELECT COUNT(*) FROM tasks")
    suspend fun getTaskCount(): Int
}
