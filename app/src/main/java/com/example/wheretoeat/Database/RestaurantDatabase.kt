package com.example.wheretoeat.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.User


@Database(entities = [Restaurant::class, User::class], version = 2, exportSchema = true)
abstract class RestaurantDatabase: RoomDatabase() {

    abstract fun RestaurantDao():RestaurantDao

    companion object{
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `user_table` (`userID` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nickname` TEXT NOT NULL, `password` TEXT NOT NULL, " +
                        "`address` TEXT NOT NULL, `email` TEXT NOT NULL, `mobile` TEXT NOT NULL)" )
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
                ).addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}