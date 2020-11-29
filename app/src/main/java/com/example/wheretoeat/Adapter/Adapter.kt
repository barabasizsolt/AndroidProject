package com.example.wheretoeat.Adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.PrimaryKey
import com.example.wheretoeat.Fragments.DetailsPageFragment
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.R
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

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.restNameView.text = currentItem.name
        holder.restPhoneView.text = "Tel: " + currentItem.phone
        holder.restPriceView.text = "Minimum price: " + currentItem.price.toString() +  "$"

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("restaurant", currentItem)
            bundle.putBoolean("visible", true)

            val detailsPageFragment = DetailsPageFragment()
            detailsPageFragment.arguments = bundle

            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, detailsPageFragment)
            transaction.commit()
        }

        val hearth = holder.itemView.findViewById<ImageView>(R.id.ic_liked)
        hearth.setOnClickListener{
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setMessage("Are you sure you want to add to Favorites?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    daoViewModel.addRestaurantDB(currentItem)
                    notifyDataSetChanged()
                    Toast.makeText(holder.itemView.context, "Item added to favorites!", Toast.LENGTH_SHORT)
                        .show()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            builder.create().show()
        }

//        holder.itemView.setOnLongClickListener {
//            val builder = AlertDialog.Builder(holder.itemView.context)
//            builder.setMessage("Are you sure you want to add to Favorites?")
//                .setCancelable(false)
//                .setPositiveButton("Yes") { dialog, id ->
//                    daoViewModel.addRestaurantDB(currentItem)
//                    notifyDataSetChanged()
//                    Toast.makeText(holder.itemView.context, "Item added to favorites!", Toast.LENGTH_SHORT)
//                        .show()
//                }
//                .setNegativeButton("No") { dialog, id ->
//                    dialog.dismiss()
//                }
//            builder.create().show()
//
//            return@setOnLongClickListener true
//        }

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