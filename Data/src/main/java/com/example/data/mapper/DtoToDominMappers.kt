package com.example.data.mapper

import com.example.data.network.dtos.UserDto
import com.example.domain.models.User


fun UserDto.toDomain() : User {
    return User(token,email)
}