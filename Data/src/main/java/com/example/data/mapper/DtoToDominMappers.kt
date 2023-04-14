package com.example.data.mapper

import com.example.data.network.dtos.ContractDto
import com.example.data.network.dtos.PostDto
import com.example.data.network.dtos.RegisterNeedDto
import com.example.domain.models.Contract
import com.example.domain.models.Post
import com.example.domain.models.RegisterNeed


fun RegisterNeedDto.toDomain():RegisterNeed {
    return RegisterNeed(posts.map { it.toDomain() },contracts.map { it.toDomain() })

}
fun PostDto.toDomain():Post {
    return Post(id,title)
}
fun ContractDto.toDomain():Contract {
    return Contract(id,title)
}
