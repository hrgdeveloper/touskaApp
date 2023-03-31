package com.example.domain.models

data class Bloc(
    val created_at: String,
    val floors: List<Floor>,
    val id: Int,
    val name: String,
    val project_id: Int,
    val updated_at: String
)