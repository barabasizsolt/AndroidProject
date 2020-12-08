package com.example.wheretoeat.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wheretoeat.Database.RestaurantDatabase
import com.example.wheretoeat.Database.RestaurantRepository
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.User
import com.example.wheretoeat.Model.UserRestaurantCross
import com.example.wheretoeat.Model.UserWithRestaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DaoViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Restaurant>>
    val readAllUser: LiveData<List<User>>
    private val repository: RestaurantRepository

    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).RestaurantDao()

        repository = RestaurantRepository(restaurantDao)
        readAllData = repository.readAllData
        readAllUser = repository.readALLUser
    }

    fun addRestaurantDB(restaurant: Restaurant) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRestaurant(restaurant)
        }
    }

    fun deleteRestaurantDB(restaurant: Restaurant) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRestaurant(restaurant)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    //------------------------------------------------------------//

    fun addUserDB(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun deleteUserDB(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
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

    //-----------------------------------------------------------//

    fun addUserRestaurantDB(userRestaurantCross: UserRestaurantCross) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserRestaurant(userRestaurantCross)
        }
    }

    suspend fun getUserWithRestaurant(userID: Int) : LiveData<List<UserWithRestaurant>> =
        withContext(viewModelScope.coroutineContext) {
            repository.getUserWithRestaurant(userID)
        }

    fun deleteAllCrossDB(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCross()
        }
    }

    suspend fun getUserCrossDB(id: Int, userID: Int):LiveData<UserRestaurantCross> =
        withContext(viewModelScope.coroutineContext) {
            repository.getUserCross(id, userID)
        }

    fun deleteCrossDB(id:Int, userID:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCross(id, userID)
        }
    }
}