package com.example.wheretoeat.Repository

import com.example.wheretoeat.Api.RetrofitInstance
import com.example.wheretoeat.Model.City
import com.example.wheretoeat.Model.Country
import com.example.wheretoeat.Model.AllRestaurant
import retrofit2.Response

class Repository {
    suspend fun getAllRestaurants(country:String, page:Int): Response<AllRestaurant>{
        return RetrofitInstance.api.getAllRestaurants(country, page)
    }
    suspend fun getCountries():Response<Country>{
        return RetrofitInstance.api.getCountries()
    }
    suspend fun getCities():Response<City>{
        return RetrofitInstance.api.getCities()
    }
}