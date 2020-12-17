package com.example.wheretoeat.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.User
import com.example.wheretoeat.Model.UserRestaurantCross


@Database(entities = [Restaurant::class, User::class, UserRestaurantCross::class], version = 3, exportSchema = true)
abstract class RestaurantDatabase: RoomDatabase() {

    abstract fun RestaurantDao():RestaurantDao

    companion object{
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `user_table` (`userID` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nickname` TEXT NOT NULL, `password` TEXT NOT NULL, " +
                        "`address` TEXT NOT NULL, `email` TEXT NOT NULL, `mobile` TEXT NOT NULL)" )
            }
        }

        private val MIGRATION_2_3: Migration = object: Migration(2,3)
        {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `favorite_restaurants` (`favoriteID` INTEGER NOT NULL ,`id` INTEGER NOT NULL,`userID` INTEGER NOT NULL, PRIMARY KEY(`favoriteID`))")
            }
        }

        @Volatile
        private var INSTANCE: RestaurantDatabase?=null

        fun getDatabase(context: Context):RestaurantDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestaurantDatabase::class.java,
                    "rest_database"
                ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}