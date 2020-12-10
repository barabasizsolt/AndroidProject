package com.example.wheretoeat.Database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.User
import com.example.wheretoeat.Model.UserRestaurantCross
import com.example.wheretoeat.Model.UserWithRestaurant

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRestaurantDao(restaurant:Restaurant)

    @Delete
    suspend fun deleteRestaurant(restaurant:Restaurant)

    @Query("DELETE FROM rest_table")
    suspend fun deleteAll()

    @Query("SELECT *FROM rest_table ORDER BY name ASC")
    fun readAllData(): LiveData<List<Restaurant>>

    //----------------------------------------------------//

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUserDao(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUser()

    @Query("SELECT * FROM user_table WHERE nickname =:nickname")
    fun getUser(nickname: String):LiveData<User>

    @Query("SELECT * FROM user_table WHERE email =:email")
    fun getUserEmail(email: String):LiveData<User>

    @Query("SELECT * FROM user_table ORDER BY nickname ASC")
    fun readAllUser():LiveData<List<User>>

    //-----------------------------------------------------//

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUserRestaurant(userRestaurantCross: UserRestaurantCross)

    @Transaction
    @Query("SELECT * FROM user_table WHERE userID =:userID")
    fun getUserWithRestaurantDao(userID: Int):LiveData<List<UserWithRestaurant>>

    @Query("DELETE FROM favorite_restaurants")
    suspend fun deleteAllCross()

    @Query("SELECT * FROM favorite_restaurants WHERE id = :id and userID =:userID")
    fun getUserCross(id:Int, userID: Int):LiveData<UserRestaurantCross>

    @Query("SELECT * FROM favorite_restaurants")
    fun getUserCrossALL():LiveData<List<UserRestaurantCross>>

    @Query("DELETE FROM favorite_restaurants where id = :id and userID = :userID")
    suspend fun deleteCross(id:Int, userID:Int)
}