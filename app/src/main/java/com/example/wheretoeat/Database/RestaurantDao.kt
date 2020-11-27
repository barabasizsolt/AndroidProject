package com.example.wheretoeat.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wheretoeat.Model.Restaurant

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
}