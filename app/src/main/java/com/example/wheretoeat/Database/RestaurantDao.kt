package com.example.wheretoeat.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.User
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

    @Query("SELECT * FROM user_table WHERE userID =:userID")
    fun getUser(userID: Int):LiveData<User>

    @Query("SELECT * FROM user_table ORDER BY nickname ASC")
    fun readAllUser():LiveData<List<User>>

    //-----------------------------------------------------//

    @Transaction
    @Query("SELECT * FROM user_table WHERE userID =:userID")
    fun getUserWithRestaurant(userID: Int):List<UserWithRestaurant>
}