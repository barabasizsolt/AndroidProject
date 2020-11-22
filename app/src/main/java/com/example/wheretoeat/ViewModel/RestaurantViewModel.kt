package com.example.wheretoeat.ViewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.wheretoeat.Database.RestaurantDatabase
import com.example.wheretoeat.Database.RestaurantRepository
import com.example.wheretoeat.Model.Country
import com.example.wheretoeat.Model.Post
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.allRestaurant
import com.example.wheretoeat.Repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class RestaurantViewModel(private val repository:Repository):ViewModel() {
    val myResponse:MutableLiveData<Response<Post>> = MutableLiveData()
    val myResponseRest:MutableLiveData<Response<Restaurant>> = MutableLiveData()
    val myResponseAll:MutableLiveData<Response<allRestaurant>> = MutableLiveData()
    val myResponseCt:MutableLiveData<Response<Country>> = MutableLiveData()

    fun getPost(){
        viewModelScope.launch {
            val response = repository.getPost()
            myResponse.value = response
        }
    }

    fun getRestaurant(){
        viewModelScope.launch {
            val response = repository.getRestaurant()
            myResponseRest.value = response
        }
    }

    fun getAllRestaurant(state:String, page:Int){
        viewModelScope.launch {
            val response = repository.getAllRestaurants(state, page)
            myResponseAll.value = response
        }
    }

    fun getCountry(){
        viewModelScope.launch {
            val response = repository.getCountires()
            myResponseCt.value = response
        }
    }
}