package com.example.wheretoeat.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_restaurants")
data class UserRestaurantCross (
    @PrimaryKey(autoGenerate = true)
    val favoriteID: Int,
    val id: Int,
    val userID: Int,
)
