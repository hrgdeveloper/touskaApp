package com.example.data.network.dtos

import com.google.gson.annotations.SerializedName

data class RegisterRequestDto(
    @SerializedName("contract_type_id")
    val contractTypeId: Int?,
    val email: String,
    val mobile: String,
    val name: String,
    @SerializedName("post_id")
    val postId: Int?,
    @SerializedName("project_id")
    val projectId: Int,
    @SerializedName("role_id")
    val roleId: Int,
    val password:String
)