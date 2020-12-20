package com.example.wheretoeat.Model

/**Storing the restaurants into the 'restaurants' attribute.*/
data class AllRestaurant(
    val total_entries:Int,
    val page:Int,
    val per_page:Int,
    val restaurants:ArrayList<Restaurant>
)