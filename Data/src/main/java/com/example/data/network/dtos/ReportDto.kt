package com.example.data.network.dtos

data class ReportDto(
    val activity: String,
    val activity_id: Int,
    val bloc_id: Int,
    val block_name: String,
    val contract_type: String,
    val created_at: String,
    val deleted_at: String?,
    val description: String?,
    val floor_id: Int?,
    val floor_name: String?,
    val id: Int,
    val post: String,
    val post_id: Int,
    val report_times: List<ReportTimeDto>,
    val submitted: String,
    val supervisor_id: Int,
    val total_time: String,
    val unit_id: Int?,
    val unit_name: String?,
    val updated_at: String,
    val worker_id: Int,
    val worker_name: String,
    val supervisorName : String?
)