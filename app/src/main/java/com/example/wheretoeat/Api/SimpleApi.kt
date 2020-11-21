package com.example.wheretoeat.Api

import com.example.wheretoeat.Model.Post
import retrofit2.Response
import retrofit2.http.GET

interface SimpleApi {
    @GET("api/stats")
    suspend fun getPost(): Response<Post>
}