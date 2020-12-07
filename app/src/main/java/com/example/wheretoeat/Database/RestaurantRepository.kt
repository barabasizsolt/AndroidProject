package com.example.wheretoeat.Database

import androidx.lifecycle.LiveData
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.User
import com.example.wheretoeat.Model.UserRestaurantCross
import com.example.wheretoeat.Model.UserWithRestaurant

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

    suspend fun deleteAllUser(){
        restaurantDao.deleteAllUser()
    }


    fun getUser(nickname:String):LiveData<User>{
        return restaurantDao.getUser(nickname)
    }

    fun getUserEmail(email:String):LiveData<User>{
        return restaurantDao.getUserEmail(email)
    }

    //--------------------------------------------------------//

    suspend fun addUserRestaurant(userRestaurantCross: UserRestaurantCross){
        restaurantDao.addUserRestaurant(userRestaurantCross)
    }

    fun getUserWithRestaurant(userID: Int):LiveData<List<UserWithRestaurant>>{
        return restaurantDao.getUserWithRestaurantDao(userID)
    }

    suspend fun deleteAllCross(){
        restaurantDao.deleteAllCross()
    }
}