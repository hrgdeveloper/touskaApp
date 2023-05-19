package com.example.data.network.dtos

data class RegisterNeedDto(
    val posts: List<PostDto>,
    val contracts: List<ContractDto>,
    val contractors : List<UserDto>
)
