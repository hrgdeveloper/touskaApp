package com.example.data.network.dtos

import com.google.gson.annotations.SerializedName

data class ReportNeedDto(
    val activities: List<ActivityDto>,
    val blocs: List<BlocDto>,
    val workingTimes: List<WorkingTimeDto>,
    @SerializedName("last_report")
    val reportDto: ReportDto?
)
