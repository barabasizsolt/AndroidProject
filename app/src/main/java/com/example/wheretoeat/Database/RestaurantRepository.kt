package com.example.wheretoeat.Database

import androidx.lifecycle.LiveData
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.User

class RestaurantRepository(private val restaurantDao: RestaurantDao) {
    val readAllData: LiveData<List<Restaurant>> = restaurantDao.readAllData()
    val readALLUser: LiveData<List<User>> = restaurantDao.readAllUser()

    suspend fun addRestaurant(restaurant: Restaurant){
        restaurantDao.addRestaurantDao(restaurant)
    }

    suspend fun deleteRestaurant(restaurant: Restaurant){
        restaurantDao.deleteRestaurant(restaurant)
    }

    suspend fun deleteAll(){
        restaurantDao.deleteAll()
    }

    //--------------------------------------------------------//

    suspend fun addUser(user: User){
        restaurantDao.addUserDao(user)
    }

    suspend fun deleteUser(user: User){
        restaurantDao.deleteUser(user)
    }

    fun getUser(userID:Int) {
        restaurantDao.getUser(userID)
    }

    //--------------------------------------------------------//

    suspend fun getUserWithRestaurant(userID: Int){
        restaurantDao.getUserWithRestaurant(userID)
    }
}