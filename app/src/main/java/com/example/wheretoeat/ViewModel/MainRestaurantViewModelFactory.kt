package com.example.wheretoeat.ViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wheretoeat.Repository.Repository

class MainRestaurantViewModelFactory(private val repository : Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RestaurantViewModel(repository) as T
    }
}