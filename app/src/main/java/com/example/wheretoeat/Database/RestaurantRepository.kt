package com.example.wheretoeat.Database

import androidx.lifecycle.LiveData
import com.example.wheretoeat.Model.*

class RestaurantRepository(private val restaurantDao: RestaurantDao) {
    val readAllRestaurant: LiveData<List<Restaurant>> = restaurantDao.readAllRestaurantDao()
    val readALLUser: LiveData<List<User>> = restaurantDao.readAllUserDao()
    val readAllCross :LiveData<List<UserRestaurantCross>> = restaurantDao.getAllUserRestaurantCrossDao()

    /**
     * Functions( ADD/GET/UPDATE) to manipulate restaurants in the database.
     * */
    /*-------------------------------------------------------------------------*/
    suspend fun addRestaurant(restaurant: Restaurant){
        restaurantDao.addRestaurantDao(restaurant)
    }
    suspend fun deleteAll(){
        restaurantDao.deleteAllRestaurantDao()
    }
    /*-------------------------------------------------------------------------*/


    /**
     * Functions( ADD/GET/UPDATE) to manipulate users in the database.
     * */
    /*-------------------------------------------------------------------------*/
    suspend fun addUser(user: User){
        restaurantDao.addUserDao(user)
    }
    suspend fun deleteAllUser(){
        restaurantDao.deleteAllUserDao()
    }
    fun getUser(nickname:String):LiveData<User>{
        return restaurantDao.getUserDao(nickname)
    }
    fun getUserEmail(email:String):LiveData<User>{
        return restaurantDao.getUserEmailDao(email)
    }
    fun updatePassword(password:String, userID: Int){
        restaurantDao.updatePasswordDao(password, userID)
    }
    /*-------------------------------------------------------------------------*/


    /**
     * Functions( ADD/GET/UPDATE) to manipulate many-to-many relationship in the database.
     * */
    /*-------------------------------------------------------------------------*/
    suspend fun addUserRestaurant(userRestaurantCross: UserRestaurantCross){
        restaurantDao.addUserRestaurantCrossDao(userRestaurantCross)
    }
    suspend fun deleteAllCross(){
        restaurantDao.deleteAllUserRestaurantCrossDao()
    }
    suspend fun deleteCross(id: Long, userID:Int){
        restaurantDao.deleteCrossDao(id, userID)
    }
    /*-------------------------------------------------------------------------*/
}