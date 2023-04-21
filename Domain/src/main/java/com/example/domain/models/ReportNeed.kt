package com.example.domain.models

data class ReportNeed(
    val activities: List<Activity>,
    val blocs: List<Bloc>,
    val workingTimes: List<WorkingTime>
)
