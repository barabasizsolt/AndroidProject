package com.example.wheretoeat.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wheretoeat.Model.*

@Dao
/**Data Access Object*/
interface RestaurantDao {
    /**
     * Functions( ADD/GET/UPDATE) to manipulate restaurants in the database.
     * */
    /*-------------------------------------------------------------------------*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRestaurantDao(restaurant:Restaurant)

    @Query("DELETE FROM rest_table")
    suspend fun deleteAllRestaurantDao()

    @Query("SELECT *FROM rest_table ORDER BY name ASC")
    fun readAllRestaurantDao(): LiveData<List<Restaurant>>
    /*-------------------------------------------------------------------------*/

    /**
     * Functions( ADD/GET/UPDATE) to manipulate users in the database.
     * */
    /*-------------------------------------------------------------------------*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUserDao(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUserDao()

    @Query("SELECT * FROM user_table WHERE nickname =:nickname")
    fun getUserDao(nickname: String):LiveData<User>

    @Query("SELECT * FROM user_table WHERE email =:email")
    fun getUserEmailDao(email: String):LiveData<User>

    @Query("SELECT * FROM user_table ORDER BY nickname ASC")
    fun readAllUserDao():LiveData<List<User>>

    @Query("UPDATE user_table SET password = :password where userID = :userID")
    fun updatePasswordDao(password:String, userID: Int)
    /*-------------------------------------------------------------------------*/

    /**
     * Functions( ADD/DELETE/GET/UPDATE) to manipulate many-to-many relationship in the database.
     * */
    /*-------------------------------------------------------------------------*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    /**
     * Many-to-Many relationship.
     * --if the current user like the current restaurant--
     * */
    suspend fun addUserRestaurantCrossDao(userRestaurantCross: UserRestaurantCross)

    @Query("DELETE FROM favorite_restaurants")
    suspend fun deleteAllUserRestaurantCrossDao()

    @Query("SELECT * FROM favorite_restaurants")
    fun getAllUserRestaurantCrossDao():LiveData<List<UserRestaurantCross>>

    /**
     * Deleting a restaurant from the current user list.
     * */
    @Query("DELETE FROM favorite_restaurants where id = :id and userID = :userID")
    suspend fun deleteCrossDao(id: kotlin.Long, userID:Int)
    /*-------------------------------------------------------------------------*/
}