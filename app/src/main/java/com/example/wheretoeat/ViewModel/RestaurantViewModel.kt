package com.example.wheretoeat.ViewModel

import androidx.lifecycle.*
import com.example.wheretoeat.Model.Country
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.allRestaurant
import com.example.wheretoeat.Repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class RestaurantViewModel(private val repository:Repository):ViewModel() {
    val myResponseAll:MutableLiveData<Response<allRestaurant>> = MutableLiveData()
    val myResponseCt:MutableLiveData<Response<Country>> = MutableLiveData()

    fun getAllRestaurant(country:String, page:Int){
        viewModelScope.launch {
            val response = repository.getAllRestaurants(country, page)
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