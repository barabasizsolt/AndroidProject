package com.example.wheretoeat.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_restaurants")
/**Junction table for many-to-many relationship.*/
data class UserRestaurantCross (
    @PrimaryKey(autoGenerate = true)
    val favoriteID: Int,
    val id: Long,
    val userID: Int,
)
