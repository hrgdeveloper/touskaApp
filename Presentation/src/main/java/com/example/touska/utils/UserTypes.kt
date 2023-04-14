package com.example.touska.utils

sealed class UserTypes(val role_id:Int){
    object Manager : UserTypes(1)
    object Owner : UserTypes(2)
    object Observer : UserTypes(3)
    object Worker : UserTypes(4)
}
