package com.example.domain.models

data class ReportTime(
    val createdAt: String,
    val endTime: String,
    val id: Int,
    val reportId: Int,
    val startTime: String,
    val updatedAt: String
)