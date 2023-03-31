package com.example.data.local.room.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.network.dtos.FloorDto

@Entity(tableName = "Bloc")
data class BlocEntity(
    val created_at: String,
    val floors: List<FloorDto>,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val project_id: Int,
    val updated_at: String
)