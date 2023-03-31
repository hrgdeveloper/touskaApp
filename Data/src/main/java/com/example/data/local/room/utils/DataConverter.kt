package com.example.data.local.room.utils

import androidx.room.TypeConverter
import com.example.data.network.dtos.FloorDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DataConverter {
    @TypeConverter
    fun fromFloorList(floors: List<FloorDto>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<FloorDto>>() {}.type
        return gson.toJson(floors,type)
    }


    @TypeConverter
    fun toFloorList(floorList: String): List<FloorDto> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<FloorDto>>() {}.type
        return gson.fromJson<List<FloorDto>>(floorList, type)
    }





}