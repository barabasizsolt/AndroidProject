package com.example.wheretoeat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wheretoeat.Fragments.ProfileFragment
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Repository.Repository
import com.example.wheretoeat.ViewModel.DaoViewModel
import com.example.wheretoeat.ViewModel.MainRestaurantViewModelFactory
import com.example.wheretoeat.ViewModel.RestaurantViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var daoViewModel: DaoViewModel
    private val listFragment = ListFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

        var countries: ArrayList<String>
        var restaurants = mutableListOf<Restaurant>()

        val repository = Repository()
        val viewModelFactory = MainRestaurantViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RestaurantViewModel::class.java)
        viewModel.getCountry()
        viewModel.myResponseCt.observe(this, Observer { response ->
            if (response.isSuccessful) {
                Log.d("Response", response.body()?.count.toString())
                Log.d("Response", response.body()?.countries.toString())

                countries = response.body()?.countries!!
                error.text = countries[0]

                for (country in countries) {
                    viewModel.getAllRestaurant(country, 1)
                    viewModel.myResponseAll.observe(this, Observer { response1 ->
                        if (response1.isSuccessful) {
                            //Log.d("Response", response1.body()?.restaurants.toString())
                            for(rest in response1.body()?.restaurants!!){
                                //restaurants.add(rest)
                                //daoViewModel.addRestaurantDB(rest)

                                error.text = rest.image_url
                                Log.d("\nResponse", rest.toString())
                            }
                        } else {
                            Log.d("Response", response1.errorBody().toString())
                            error.text = response1.code().toString()
                        }
                    })
                }
            } else {
                Log.d("Response", response.errorBody().toString())
                error.text = response.code().toString()
            }
        })

        daoViewModel.addRestaurantDB(Restaurant(1,"x","x","x","","","","","",0.1,0.1, 2,"","",""))

//        bottomNav.setOnNavigationItemSelectedListener {
//            var selectedFragment: Fragment = ListFragment()
//            when (it.itemId) {
//                R.id.list_food -> selectedFragment = listFragment
//                R.id.add_food -> selectedFragment = profileFragment
//
//            }
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.nav_host_fragment, selectedFragment)
//            transaction.commit()
//            return@setOnNavigationItemSelectedListener true
//        }
    }
}