package com.example.wheretoeat.Repository

import com.example.wheretoeat.Api.RetrofitInstance
import com.example.wheretoeat.Model.Country
import com.example.wheretoeat.Model.Post
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.allRestaurant
import retrofit2.Response

class Repository {
    suspend fun getPost(): Response<Post> {
        return RetrofitInstance.api.getPost()
    }
    suspend fun getRestaurant(): Response<Restaurant>{
        return RetrofitInstance.api.getRestaurant()
    }
    suspend fun getAllRestaurants(state:String, page:Int): Response<allRestaurant>{
        return RetrofitInstance.api.getAllRestaurants(state, page)
    }
    suspend fun getCountires():Response<Country>{
        return RetrofitInstance.api.getCountries()
    }
}