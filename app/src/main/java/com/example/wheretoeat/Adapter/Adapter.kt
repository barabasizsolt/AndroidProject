package com.example.wheretoeat.Adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.Fragments.DetailsPageFragment
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.UserRestaurantCross
import com.example.wheretoeat.R
import com.example.wheretoeat.Util.Constants
import com.example.wheretoeat.ViewModel.DaoViewModel
import kotlinx.coroutines.runBlocking
import java.util.*


class Adapter(private var daoViewModel: DaoViewModel, mContext: Context, mvVewLifecycleOwner : LifecycleOwner) : RecyclerView.Adapter<Adapter.ViewHolder>(){
    private var exampleList: MutableList<Restaurant> = mutableListOf()
    private var context = mContext
    private var viewLifecycleO = mvVewLifecycleOwner

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycle_element,
            parent, false
        )
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
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

                    var flag:Boolean = false
                    daoViewModel.readAllCross.observe(viewLifecycleO, {cr->

                        for(v in cr){
                            if(v.id == currentItem.id && v.userID == Constants.user.userID){
                                flag = true
                            }
                        }

                        if(!flag){
                            val randomNumber: Int = Random().nextInt(100000)
                            Constants.userLs.add(currentItem)
                            daoViewModel.addUserRestaurantDB(UserRestaurantCross(randomNumber, currentItem.id, Constants.user.userID ))
                            notifyDataSetChanged()
                            Toast.makeText(holder.itemView.context, "Item added to favorites!", Toast.LENGTH_SHORT).show()
                        }
                    })

                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            builder.create().show()
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