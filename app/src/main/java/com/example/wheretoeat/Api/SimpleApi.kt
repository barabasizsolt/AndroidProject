package com.example.wheretoeat.Api

import com.example.wheretoeat.Model.City
import com.example.wheretoeat.Model.AllRestaurant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**Retrofit turns your HTTP API into a Java/Kotlin interface.*/
interface SimpleApi {
    @GET("/restaurants")
    suspend fun getAllRestaurants(
        @Query("city") city : String,
        @Query("page") page : Int
    ): Response<AllRestaurant>

    /**Make a synchronous or asynchronous HTTP request to the remote web server.
     * Get the cities from the server.*/
    @GET("/cities")
    suspend fun getCities(): Response<City>
}