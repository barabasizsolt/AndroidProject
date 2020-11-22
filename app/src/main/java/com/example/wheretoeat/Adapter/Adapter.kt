package com.example.wheretoeat.Adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.R
import com.example.wheretoeat.ViewModel.DaoViewModel

class Adapter(private var daoViewModel: DaoViewModel) : RecyclerView.Adapter<Adapter.FoodViewHolder>() {
    private var exampleList: MutableList<Restaurant> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycle_element,
            parent, false
        )
        return FoodViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.restNameView.text = currentItem.name
        holder.restPhoneView.text = currentItem.phone
        holder.restPriceView.text = currentItem.price.toString()
    }

    override fun getItemCount() = exampleList.size

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restNameView: TextView = itemView.findViewById(R.id.restaurantName)
        val restPhoneView: TextView = itemView.findViewById(R.id.restaurantPhone)
        val restPriceView: TextView = itemView.findViewById(R.id.restaurantPrice)
    }

    fun setData(food:MutableList<Restaurant>){
        this.exampleList = food
        notifyDataSetChanged()
    }
}