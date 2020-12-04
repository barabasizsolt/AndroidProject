package com.example.wheretoeat.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (
    @PrimaryKey(autoGenerate = true)
    var userID: Int = 0,
    val nickname: String,
    val password: String,
    val address: String,
    val email:String,
    val mobile:String
)
