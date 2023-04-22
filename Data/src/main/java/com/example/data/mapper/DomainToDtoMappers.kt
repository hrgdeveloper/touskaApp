package com.example.data.mapper

import com.example.data.network.dtos.WorkingTimeDto
import com.example.domain.models.WorkingTime

fun WorkingTime.toDto():WorkingTimeDto{
    return WorkingTimeDto(id,title,startTime,endTime)
}