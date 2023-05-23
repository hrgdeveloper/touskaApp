package com.example.domain.models

data class User(
    val id:Int,
    val contract_type_id: Int?,
    val created_at: String?,
    val email: String,
    val mobile: String,
    val name: String,
    val post_id: Int?,
    val role_id: Int,
    val status: Int,
    val role:String,
    val project : Project?,
    val contractorId:Int?,
    val contractorName:String?,
    val profile:String?
)