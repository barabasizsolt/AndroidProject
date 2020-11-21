package com.example.wheretoeat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wheretoeat.Repository.Repository
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.error

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val viewModelFactory = MainRestaurantViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RestaurantViewModel::class.java)
        viewModel.getPost()
        viewModel.myResponse.observe(this, Observer { response ->
            if(response.isSuccessful){
                Log.d("Response", response.body()?.countries.toString())
                Log.d("Response", response.body()?.cities.toString())
                Log.d("Response", response.body()?.restaurants.toString())
                error.text = response.body()?.countries.toString()

            }
            else{
                Log.d("Response",response.errorBody().toString())
                error.text = response.code().toString()
            }
        })
    }
}