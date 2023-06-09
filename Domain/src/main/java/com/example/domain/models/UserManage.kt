package com.example.domain.models

data class UserManage(
    val contractTypeId: Int?,
    val createdAt: String,
    val email: String,
    val emailVerifiedAt: Boolean?,
    val id: Int,
    val mobile: String,
    val name: String,
    val postId: Int?,
    val projectId: Int,
    val roleId: Int,
    val status: Int,
    val updatedAt: String,
    val profile : String?,
    val contractType : String?,
    val postTitle:String?,
    val qrCode : String?,
    val roleTitle : String,
    val contractorId:Int?,
    val contractorName:String?,
    val description:String?
)