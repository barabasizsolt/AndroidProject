package com.example.wheretoeat.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wheretoeat.MainActivity
import com.example.wheretoeat.R
import com.example.wheretoeat.Repository.Repository
import com.example.wheretoeat.ViewModel.MainRestaurantViewModelFactory
import com.example.wheretoeat.ViewModel.RestaurantViewModel
import com.example.wheretoeat.Util.Constants.Companion.cities
import kotlinx.coroutines.runBlocking

/**SplashActivity, getting the cities from the API*/
@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {
    private lateinit var viewModel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        /**Storing the cities into the 'Constants.cities' list.*/
        val repository = Repository()
        val viewModelFactory = MainRestaurantViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RestaurantViewModel::class.java)
        viewModel.getCity()
        viewModel.myResponseCity.observe(this, Observer { response ->
            if (response.isSuccessful) {
                cities = response.body()?.cities!!
            }
        })

        /**Make the application run in full screen mode.*/
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        /**After 3,5 sec starting the mainActivity*/
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3500)
    }
}