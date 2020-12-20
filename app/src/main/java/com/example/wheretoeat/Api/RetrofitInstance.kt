package com.example.wheretoeat.Api

import com.example.wheretoeat.Util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**The Retrofit object generates an implementation of the 'SimpleApi' interface.*/
object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    /**Lazy initialization*/
    val api:SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }
}