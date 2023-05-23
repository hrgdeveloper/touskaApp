package com.example.data.network.dtos

data class ReportNeedFullDto(
    val activities: List<ActivityDto>,
    val blocs: List<BlocDto>,
    val contracts : List<ContractDto>,
    val posts : List<PostDto>,
    val floors : List<FloorDto>,
    val units : List<UnitDto>,
    val contractors : List<UserDto>
)
