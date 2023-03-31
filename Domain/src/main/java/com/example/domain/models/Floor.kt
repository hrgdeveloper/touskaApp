package com.example.domain.models



data class Floor(
    val bloc_id: Int,
    val created_at: String,
    val id: Int,
    val name: String,
    val number: Int,
    val unitts: List<Unitt>,
    val updated_at: String,
    var is_open : Boolean = false
)