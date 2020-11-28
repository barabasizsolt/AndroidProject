package com.example.wheretoeat.Api

import com.example.wheretoeat.Model.City
import com.example.wheretoeat.Model.Country
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.allRestaurant
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpleApi {
    @GET("api/restaurants")
    suspend fun getAllRestaurants(
        @Query("city") city : String,
        @Query("page") page : Int
    ): Response<allRestaurant>

    @GET("api/countries")
    suspend fun getCountries(): Response<Country>

    @GET("api/cities")
    suspend fun getCities(): Response<City>
}