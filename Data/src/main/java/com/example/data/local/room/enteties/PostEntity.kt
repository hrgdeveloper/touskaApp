package com.example.data.local.room.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Post")
data class PostEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
)