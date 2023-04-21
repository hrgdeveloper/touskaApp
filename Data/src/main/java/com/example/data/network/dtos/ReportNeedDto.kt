package com.example.data.network.dtos

data class ReportNeedDto(
    val activities: List<ActivityDto>,
    val blocs: List<BlocDto>,
    val workingTimes: List<WorkingTimeDto>
)
