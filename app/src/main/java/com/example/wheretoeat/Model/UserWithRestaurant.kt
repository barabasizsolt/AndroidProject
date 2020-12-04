package com.example.wheretoeat.Model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithRestaurant (
    @Embedded val user:User,
    @Relation(
        parentColumn = "userID",
        entityColumn = "id",
    )
    val restaurants: List<Restaurant>
)
