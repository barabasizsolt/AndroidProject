package com.example.wheretoeat.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.wheretoeat.Database.RestaurantDatabase
import com.example.wheretoeat.Database.RestaurantRepository
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class DaoViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Restaurant>>
    val readAllUser: LiveData<List<User>>
    private val repository: RestaurantRepository

    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).RestaurantDao()

        repository = RestaurantRepository(restaurantDao)
        readAllData = repository.readAllData
        readAllUser = repository.readALLUser
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

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    //------------------------------------------------------------//

    fun addUserDB(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun deleteUserDB(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun getUserDB(userID:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUser(userID)
        }
    }

    //-----------------------------------------------------------//

    fun getUserWithRestaurant(userID: Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.getUserWithRestaurant(userID)
        }
    }
}