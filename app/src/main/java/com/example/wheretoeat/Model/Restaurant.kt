package com.example.wheretoeat.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rest_table")
data class Restaurant(
    @PrimaryKey var id: Int,
    var name: String,
    var address: String,
    var city: String,
    var state: String,
    var area: String,
    var postal_code: String,
    var country: String,
    var phone: String,
    var lat: Double,
    var lng: Double,
    var price: Int,
    var reserve_url: String,
    var mobile_reserve_url: String,
    var image_url: String
)
