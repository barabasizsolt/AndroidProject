package com.example.wheretoeat.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.wheretoeat.Database.RestaurantDatabase
import com.example.wheretoeat.Database.RestaurantRepository
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.User
import com.example.wheretoeat.Model.UserRestaurantCross
import kotlinx.coroutines.*

class DaoViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Restaurant>>
    val readAllUser: LiveData<List<User>>
    val readAllCross:LiveData<List<UserRestaurantCross>>
    private val repository: RestaurantRepository

    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).restaurantDao()
        repository = RestaurantRepository(restaurantDao)
        readAllData = repository.readAllRestaurant
        readAllUser = repository.readALLUser
        readAllCross = repository.readAllCross
    }

    /**
     * Functions to manipulate restaurants in the database.
     * */
    fun addRestaurantDB(restaurant: Restaurant) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRestaurant(restaurant)
        }
    }
    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
    /*-----------------------------------------------------------------------------*/


    /**
     * Functions to manipulate users in the database.
     * */
    fun addUserDB(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }
    fun deleteAllUserDB(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUser()
        }
    }
    suspend fun getUserDB(nickname: String): LiveData<User> =
        withContext(viewModelScope.coroutineContext) {
            repository.getUser(nickname)
        }
    suspend fun getUserEmailDB(email: String): LiveData<User> =
        withContext(viewModelScope.coroutineContext) {
            repository.getUserEmail(email)
        }
    fun updatePasswordDB(password:String, userID: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePassword(password, userID)
        }
    }
    /*-----------------------------------------------------------------------------*/

    /**
     * Functions to manipulate many-to-many relations in the database.
     * */
    fun addUserRestaurantDB(userRestaurantCross: UserRestaurantCross) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserRestaurant(userRestaurantCross)
        }
    }
    fun deleteAllCrossDB(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCross()
        }
    }
    fun deleteCrossDB(id: Long, userID:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCross(id, userID)
        }
    }
    /*-------------------------------------------------------------------------*/
}