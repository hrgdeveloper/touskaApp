package com.example.data.local.room.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "WorkingTime")
data class WorkingTimeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val startTime : String,
    val endTime :String
)