package com.example.domain.models

data class Report(
    val activity: String,
    val activityId: Int,
    val blocId: Int,
    val blockName: String,
    val contractType: String,
    val createdAt: String,
    val deletedAt: String?,
    val description: String?,
    val floorId: Int?,
    val floorName: String?,
    val id: Int,
    val post: String,
    val postId: Int,
    val reportTimes: List<ReportTime>,
    val submitted: String,
    val supervisorId: Int,
    val totalTime: String,
    val unitId: Int?,
    val unitName: String?,
    val updatedAt: String,
    val workerId: Int,
    val workerName: String,
    val supervisorName : String?
)