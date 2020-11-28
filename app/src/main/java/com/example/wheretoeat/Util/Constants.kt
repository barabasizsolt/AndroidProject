package com.example.wheretoeat.Util

import com.example.wheretoeat.Model.Restaurant

class Constants {
    companion object {
        const val BASE_URL = "http://opentable.herokuapp.com"
        var cities: ArrayList<String> = arrayListOf()
        var pages: MutableMap<String, Int> = hashMapOf()
    }
}