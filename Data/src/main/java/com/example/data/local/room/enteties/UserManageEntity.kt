package com.example.data.local.room.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserManage")
data class UserManageEntity(
    val contract_type_id: Int?,
    val created_at: String,
    val email: String,
    val email_verified_at: Boolean?,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val mobile: String,
    val name: String,
    val post_id: Int?,
    val project_id: Int,
    val role_id: Int,
    val status: Int,
    val updated_at: String,
    val profile :String?,
    val contract_type : String? ,
    val post_title : String?,
    val qr_code:String,
    val role_title:String,
    val contractor_id:Int?,
    val contractorName:String?,
    val description:String?

)