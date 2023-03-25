package com.example.data.mapper

import com.example.data.local.room.enteties.ProjectEntity
import com.example.data.local.room.enteties.UserEntity
import com.example.data.network.dtos.UserDto
import com.example.domain.models.Project
import com.example.domain.models.User


fun UserEntity.toDomain():User {
    return User(contract_type_id,created_at,email,mobile,name,post_id,role_id,status,role,project.toDomain())
}

fun ProjectEntity.toDomain() : Project{
    return Project(project_id,name,lat, lang,pic)
}