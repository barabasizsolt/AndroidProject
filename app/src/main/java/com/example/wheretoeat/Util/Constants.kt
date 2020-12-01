package com.example.wheretoeat.Util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.R

class Constants {
    companion object {
        const val BASE_URL = "http://opentable.herokuapp.com"
        var cities: ArrayList<String> = arrayListOf()
        var pages: MutableMap<String, Int> = hashMapOf()
        val myImageList = intArrayOf(R.drawable.avatar11, R.drawable.avatar22, R.drawable.avatar33, R.drawable.avatar44)
    }
}