package com.example.data.local.room.enteties

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val contract_type_id: Int?,
    val created_at: String?,
    val email: String,
    val mobile: String,
    val name: String,
    val post_id: Int?,
    val role_id: Int,
    val status: Int,
    val role:String,
    @Embedded(prefix = "pj")
    val project : ProjectEntity,
    val contractor_id : Int?,
    val contractorName:String?

)