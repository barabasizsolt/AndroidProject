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
import com.example.wheretoeat.ViewModel.DaoViewModel
import com.example.wheretoeat.ViewModel.MainRestaurantViewModelFactory
import com.example.wheretoeat.ViewModel.RestaurantViewModel
import kotlin.math.round

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var daoViewModel: DaoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

        var countries: ArrayList<String>

        val repository = Repository()
        val viewModelFactory = MainRestaurantViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RestaurantViewModel::class.java)
        viewModel.getCountry()
        //Kiveszem a country-on
        viewModel.myResponseCt.observe(this, Observer { response ->
            if (response.isSuccessful) {
                //Log.d("Number", response.body()?.count.toString())
               // Log.d("States", response.body()?.countries.toString())

                countries = response.body()?.countries!!//country list

                //Vegigmegyek a country-on
                for (country in countries) {
                    viewModel.getAllRestaurant(country, 1)
                    viewModel.myResponseAll.observe(this, Observer { response1 ->
                        if (response1.isSuccessful) {
                            Log.d("Current country", country)
                            Log.d("Entries", response1.body()?.total_entries.toString() + "\n\n")

                            for(rest in response1.body()?.restaurants!!){
                                daoViewModel.addRestaurantDB(rest)
                                Log.d("\nResponse", rest.toString())
                            }
                        }
                    })
                }
            }
        })

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}