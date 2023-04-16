package com.example.data.network.dtos

import com.google.gson.annotations.SerializedName

data class UserManageDto(
    @SerializedName("contract_type_id")
    val contractTypeId: Int?,
    @SerializedName("created_at")
    val createdAt: String,
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Boolean?,
    val id: Int,
    val mobile: String,
    val name: String,
    @SerializedName("post_id")
    val postId: Int?,
    @SerializedName("project_id")
    val projectId: Int,
    @SerializedName("role_id")
    val roleId: Int,
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    val profile : String?,
    @SerializedName("contract_type")
    val contractType : String?,
    @SerializedName("post_title")
    val postTitle:String?,
    @SerializedName("qr_code")
    val qrCode:String
)