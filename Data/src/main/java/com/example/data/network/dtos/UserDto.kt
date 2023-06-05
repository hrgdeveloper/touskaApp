package com.example.data.network.dtos


data class UserDto(
    val id : Int,
    val contract_type_id: Int?,
    val created_at: String?,
    val email: String,
    val mobile: String,
    val name: String,
    val post_id: Int?,
    val role_id: Int,
    val status: Int,
    val token:String,
    val role:String,
    val project: ProjectDto,
    val contractor_id:Int?,
    val contractorName:String?,
    val profile:String?,
    val description:String?

)