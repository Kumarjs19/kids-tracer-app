package com.example.kidstracer.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "numbers")
data class Number(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val digit: String,
    val tracePath: String = "", // JSON string representing the trace path
    val imageUrl: String = "",
    val difficulty: Int = 1 // 1-5 difficulty level
)