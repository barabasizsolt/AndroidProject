package com.example.wheretoeat.Repository

import com.example.wheretoeat.Api.RetrofitInstance
import com.example.wheretoeat.Model.Country
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.allRestaurant
import retrofit2.Response

class Repository {
    suspend fun getAllRestaurants(country:String, page:Int): Response<allRestaurant>{
        return RetrofitInstance.api.getAllRestaurants(country, page)
    }
    suspend fun getCountires():Response<Country>{
        return RetrofitInstance.api.getCountries()
    }
}