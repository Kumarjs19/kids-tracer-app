package com.example.kidstracer.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "letters")
data class Letter(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val character: String,
    val uppercase: Boolean = true,
    val tracePath: String = "", // JSON string representing the trace path
    val imageUrl: String = "",
    val difficulty: Int = 1 // 1-5 difficulty level
)