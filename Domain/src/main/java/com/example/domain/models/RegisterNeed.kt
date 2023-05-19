package com.example.domain.models



data class RegisterNeed(
    val posts: List<Post>,
    val contracts: List<Contract>,
    val contractors : List<User>
)
