package com.example.data.mapper

import com.example.data.local.room.enteties.UserEntity
import com.example.data.network.dtos.UserDto
import com.example.domain.models.User

fun UserDto.toEntity() : UserEntity {
    return UserEntity(id,contract_type_id,created_at,email,mobile,name,post_id,project_id,role_id,status,token,updated_at)
}