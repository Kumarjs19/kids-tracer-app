package com.example.kidstracer.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val letterOrNumberId: Int,
    val type: String, // "letter" or "number"
    val isCompleted: Boolean = false,
    val accuracy: Float = 0f, // 0-100%
    val attempts: Int = 0,
    val dateCompleted: Long = 0L,
    val stars: Int = 0 // 0-3 stars based on accuracy
)