package com.example.data.network.dtos

data class BlocDto(
    val created_at: String,
    val floors: List<FloorDto>,
    val id: Int,
    val name: String,
    val project_id: Int,
    val updated_at: String
)