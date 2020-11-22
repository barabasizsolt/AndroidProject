package com.example.wheretoeat.Api

import com.example.wheretoeat.Model.Country
import com.example.wheretoeat.Model.Post
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.allRestaurant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpleApi {
    @GET("api/stats")
    suspend fun getPost(): Response<Post>

    @GET("api/restaurants")
    suspend fun getRestaurant(
    ): Response<Restaurant>

    @GET("api/restaurants")
    suspend fun getAllRestaurants(
        @Query("state") state : String,
        @Query("page") page : Int
    ): Response<allRestaurant>

    @GET("api/countries")
    suspend fun getCountries(): Response<Country>
}