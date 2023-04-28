package com.example.data.network.dtos

data class ReportTimeDto(
    val created_at: String,
    val end_time: String,
    val id: Int,
    val report_id: Int,
    val start_time: String,
    val updated_at: String
)