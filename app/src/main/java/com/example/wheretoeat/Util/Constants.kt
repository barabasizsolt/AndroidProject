package com.example.wheretoeat.Util

import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.User
import com.example.wheretoeat.R

/**Helper class.*/
class Constants {
    companion object {
        const val BASE_URL = "https://ratpark-api.imok.space"
        /**Store the cities after receiving them from the API.*/
        var cities: ArrayList<String> = arrayListOf()
        /**The current user's favorite restaurants.*/
        var userList = mutableListOf<Restaurant>()
        /**The avatars's drawable id.*/
        val myImageList = intArrayOf(R.drawable.avatar11, R.drawable.avatar22, R.drawable.avatar33, R.drawable.avatar44)
        /**The cover photos's drawable id.*/
        val myImageRest = mutableListOf(R.drawable.out, R.drawable.inside2, R.drawable.menu)
        /**The current user.*/
        lateinit var user: User
    }
}