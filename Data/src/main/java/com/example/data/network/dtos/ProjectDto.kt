package com.example.data.network.dtos

data class ProjectDto(
    val created_at: Any,
    val id: Int,
    val lat: Double,
    val lng: Double,
    val name: String,
    val pic: String,
    val updated_at: Any
)