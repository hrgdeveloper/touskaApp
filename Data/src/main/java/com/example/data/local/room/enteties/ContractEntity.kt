package com.example.data.local.room.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Contract")
data class ContractEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
)