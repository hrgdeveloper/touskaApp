package com.example.data.network.dtos

data class FloorDto(
    val bloc_id: Int,
    val created_at: String,
    val id: Int,
    val name: String,
    val number: Int,
    val units: List<UnitDto>,
    val updated_at: String
)