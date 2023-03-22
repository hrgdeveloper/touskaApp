package com.example.data.network.dtos


data class UserDto(
    val id : Int,
    val contract_type_id: Int?,
    val created_at: String,
    val email: String,
    val mobile: String,
    val name: String,
    val post_id: Int?,
    val project_id: Int,
    val role_id: Int,
    val status: Int,
    val updated_at: String,
    val token:String
)