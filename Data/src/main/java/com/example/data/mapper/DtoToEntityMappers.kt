package com.example.data.mapper

import com.example.data.local.room.enteties.ProjectEntity
import com.example.data.local.room.enteties.UserEntity
import com.example.data.network.dtos.ProjectDto
import com.example.data.network.dtos.UserDto
import com.example.domain.models.User

fun UserDto.toEntity() : UserEntity {
    return UserEntity(id,contract_type_id,created_at,email,mobile,name,post_id,role_id,status,role,
           project.toEntity()
        )
}

fun ProjectDto.toEntity():ProjectEntity {
    return ProjectEntity(id,name,lat, lng,pic)
}