package com.example.wheretoeat.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.wheretoeat.Database.RestaurantDatabase
import com.example.wheretoeat.Database.RestaurantRepository
import com.example.wheretoeat.Model.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DaoViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Restaurant>>
    private val repository: RestaurantRepository

    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).RestaurantDao()
        repository = RestaurantRepository(restaurantDao)
        readAllData = repository.readAllData
    }

    fun addRestaurantDB(restaurant: Restaurant){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRestaurant(restaurant)
        }
    }

    fun deleteRestaurantDB(restaurant: Restaurant){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRestaurant(restaurant)
        }
    }
}