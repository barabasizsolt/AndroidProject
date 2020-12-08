package com.example.wheretoeat.Adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.Fragments.DetailsPageFragment
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.R
import com.example.wheretoeat.Util.Constants
import com.example.wheretoeat.ViewModel.DaoViewModel
import kotlinx.coroutines.runBlocking

class FavoriteAdapter (private var daoViewModel: DaoViewModel, mContext: Context) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){
    private var exampleList: MutableList<Restaurant> = mutableListOf()
    private  var context = mContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycle_element,
            parent, false
        )
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.restNameView.text = currentItem.name
        holder.restPhoneView.text = "Tel: " + currentItem.phone
        holder.restPriceView.text = "Minimum price: " + currentItem.price.toString() +  "$"

        when{
            currentItem.id % 2 == 0 -> {
                holder.restImageView.setImageResource(R.drawable.foodimage5)
            }
            currentItem.id % 3 == 0 -> {
                holder.restImageView.setImageResource(R.drawable.foodimage4)
            }
        }

        val hearth = holder.itemView.findViewById<ImageView>(R.id.ic_liked)
        hearth.isVisible = false

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("restaurant", currentItem)
            bundle.putBoolean("visible", false)

            val detailsPageFragment = DetailsPageFragment()
            detailsPageFragment.arguments = bundle

            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, detailsPageFragment)
            transaction.commit()
        }

        holder.itemView.setOnLongClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    daoViewModel.deleteCrossDB(exampleList[position].id, Constants.user.userID)

                    exampleList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyDataSetChanged()

                    Toast.makeText(holder.itemView.context, "Item deleted!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            builder.create().show()

            return@setOnLongClickListener true
        }
    }


    override fun getItemCount() = exampleList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restNameView: TextView = itemView.findViewById(R.id.restaurantName)
        val restPhoneView: TextView = itemView.findViewById(R.id.restaurantPhone)
        val restPriceView: TextView = itemView.findViewById(R.id.restaurantPrice)
        var restImageView: ImageView = itemView.findViewById(R.id.foodImage)
    }

    fun setData(restaurants: MutableList<Restaurant>){
        this.exampleList = restaurants
        notifyDataSetChanged()
    }

}