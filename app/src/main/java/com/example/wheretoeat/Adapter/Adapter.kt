package com.example.wheretoeat.Adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.Fragments.DetailsPageFragment
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.R
import com.example.wheretoeat.Util.DoubleClickListener
import com.example.wheretoeat.ViewModel.DaoViewModel


class Adapter(private var daoViewModel: DaoViewModel, mContext: Context) : RecyclerView.Adapter<Adapter.FoodViewHolder>(){
    private var exampleList: MutableList<Restaurant> = mutableListOf()
    private  var context = mContext

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

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name", currentItem.name)
            bundle.putString("phone", currentItem.phone)
            bundle.putString("price", currentItem.price.toString())

            val detailsPageFragment = DetailsPageFragment()
            detailsPageFragment.arguments = bundle

            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, detailsPageFragment)
            transaction.commit()
        }

        holder.itemView.setOnClickListener(object : DoubleClickListener() {
            override fun onDoubleClick(v: View) {
                daoViewModel.addRestaurantDB(exampleList[position])
                notifyDataSetChanged()
                Toast.makeText(holder.itemView.context,"Added to favorites!",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getItemCount() = exampleList.size

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restNameView: TextView = itemView.findViewById(R.id.restaurantName)
        val restPhoneView: TextView = itemView.findViewById(R.id.restaurantPhone)
        val restPriceView: TextView = itemView.findViewById(R.id.restaurantPrice)
    }

    fun setData(restaurants: MutableList<Restaurant>){
        this.exampleList = restaurants
        notifyDataSetChanged()
    }
}