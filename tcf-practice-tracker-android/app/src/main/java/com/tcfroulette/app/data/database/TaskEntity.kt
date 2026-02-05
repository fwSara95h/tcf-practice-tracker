package com.tcfroulette.app.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "task_type")
    val taskType: String,

    @ColumnInfo(name = "format_category")
    val formatCategory: String?,

    @ColumnInfo(name = "prompt")
    val prompt: String,

    @ColumnInfo(name = "scenario_category")
    val scenarioCategory: String?
) {
    val displayFormat: String
        get() = formatCategory ?: "Format?"

    val displayScenario: String
        get() = scenarioCategory ?: "General"
}
