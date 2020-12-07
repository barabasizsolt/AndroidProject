package com.example.wheretoeat.Model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithRestaurant (
    @Embedded val user:User,
    @Relation(
        parentColumn = "userID",
        entityColumn = "id",
        associateBy = Junction(UserRestaurantCross::class)
    )
    val restaurants: List<Restaurant>
)
