package com.example.data.network.dtos

import com.google.gson.annotations.SerializedName

data class WorkingTimeDto(
    val id: Int,
    val title: String,
    @SerializedName("start_time")
    val startTime : String,
    @SerializedName("end_time")
    val endTime :String
)