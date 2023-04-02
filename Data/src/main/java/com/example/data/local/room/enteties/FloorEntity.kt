package com.example.data.local.room.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Floor")
data class FloorEntity(
    val bloc_id: Int,
    val created_at: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val number: Int ,
    val updated_at: String,

)