package com.example.wheretoeat.Api

import com.example.wheretoeat.Model.City
import com.example.wheretoeat.Model.AllRestaurant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpleApi {
//    @GET("api/restaurants")
//    suspend fun getAllRestaurants(
//        @Query("city") city : String,
//        @Query("page") page : Int
//    ): Response<AllRestaurant>
//
//    @GET("api/cities")
//    suspend fun getCities(): Response<City>

    @GET("/restaurants")
    suspend fun getAllRestaurants(
        @Query("city") city : String,
        @Query("page") page : Int
    ): Response<AllRestaurant>

    @GET("/cities")
    suspend fun getCities(): Response<City>
}