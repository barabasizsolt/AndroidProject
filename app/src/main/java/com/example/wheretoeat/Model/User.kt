package com.example.wheretoeat.Model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "user_table")
@Parcelize
data class User (
    @PrimaryKey(autoGenerate = true)
    var userID: Int = 0,
    val nickname: String,
    val password: String,
    val address: String,
    val email:String,
    val mobile:String
): Parcelable
