package com.example.data.local.room.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Unitt")
data class UnitEntity(
    val created_at: String,
    val floor_id: Int,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val updated_at: String
)