package com.example.data.network.dtos

data class ReportDto(
    val activity_id: String,
    val bloc_id: String,
    val created_at: String,
    val description: String,
    val floor_id: String,
    val id: Int,
    val post_id: Int,
    val submitted: String,
    val supervisor_id: String,
    val unit_id: String,
    val updated_at: String,
    val worker_id: String
)