package com.example.touska.utils

sealed class FilterTypes(val type:Int){
    object BLOCK : FilterTypes(1)
    object FLOOR : FilterTypes(2)
    object UNIT : FilterTypes(3)
    object POST : FilterTypes(4)
    object ACTIVITY : FilterTypes(5)
    object CONTRACT : FilterTypes(6)

    object START_TIME : FilterTypes(7)

    object END_TIME : FilterTypes(8)
}
