package com.tcfroulette.app.data.repository

import android.util.Log
import com.tcfroulette.app.data.database.AppDatabase
import com.tcfroulette.app.data.database.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(private val database: AppDatabase) {
    companion object {
        private const val TAG = "TaskRepository"
    }

    suspend fun getRandomTask(): TaskEntity? = withContext(Dispatchers.IO) {
        Log.d(TAG, "getRandomTask() called")
        try {
            val task = database.taskDao().getRandomTask()
            if (task != null) {
                Log.d(TAG, "Got task #${task.id}: category=${task.category}, taskType=${task.taskType}")
            } else {
                Log.w(TAG, "getRandomTask() returned null - database may be empty")
            }
            task
        } catch (e: Exception) {
            Log.e(TAG, "getRandomTask() failed", e)
            throw e
        }
    }

    suspend fun getTaskCount(): Int = withContext(Dispatchers.IO) {
        Log.d(TAG, "getTaskCount() called")
        try {
            val count = database.taskDao().getTaskCount()
            Log.d(TAG, "Task count: $count")
            count
        } catch (e: Exception) {
            Log.e(TAG, "getTaskCount() failed", e)
            throw e
        }
    }
}
