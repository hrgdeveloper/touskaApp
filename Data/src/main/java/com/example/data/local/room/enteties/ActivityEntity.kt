package com.example.data.local.room.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("activity")
data class ActivityEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val post_id:Int
)