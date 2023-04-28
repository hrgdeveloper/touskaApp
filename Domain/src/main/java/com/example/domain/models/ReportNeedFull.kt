package com.example.domain.models

data class ReportNeedFull(
    val activities: List<Activity>,
    val blocs: List<Bloc>,
    val contracts : List<Contract>,
    val posts : List<Post>,
    val floors : List<Floor>,
    val units : List<Unitt>
)
