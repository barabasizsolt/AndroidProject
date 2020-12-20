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
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.Fragments.DetailsPageFragment
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.UserRestaurantCross
import com.example.wheretoeat.R
import com.example.wheretoeat.Util.Constants
import com.example.wheretoeat.ViewModel.DaoViewModel
import java.util.*

/**An 'Adapter' object acts as a bridge between an AdapterView and the underlying data for that view.*/
class Adapter(private var daoViewModel: DaoViewModel, mContext: Context, mvVewLifecycleOwner : LifecycleOwner) : RecyclerView.Adapter<Adapter.ViewHolder>(){
    private var exampleList: MutableList<Restaurant> = mutableListOf()
    private var context = mContext
    private var viewLifecycleO = mvVewLifecycleOwner

    /**RecyclerView calls this method whenever it needs to create a new ViewHolder.*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycle_element, parent, false)
        return ViewHolder(itemView)
    }

    /**RecyclerView calls this method to associate a ViewHolder with data.*/
    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = exampleList[position]

        /**Setting up the current item(restaurant:name, phone, price) attributes.*/
        holder.restNameView.text = currentItem.name
        holder.restPhoneView.text = "Tel: " + currentItem.phone
        holder.restPriceView.text = "Minimum price: " + "$".repeat(currentItem.price)

        /**Randomly generating a photo to the current restaurant, based on his ID.*/
        when{
            currentItem.id % 2 == 0.toLong() -> {
                holder.restImageView.setImageResource(R.drawable.foodimage5)
            }
            currentItem.id % 3 == 0.toLong() && currentItem.id % 2 != 0.toLong() -> {
                holder.restImageView.setImageResource(R.drawable.foodimage4)
            }
        }

        /**
         * Setting up an 'setOnClickListener' to the current item. If the user click on the
         * current restaurant, it will show up the restaurant details.
         */
        holder.itemView.setOnClickListener {
            /**Making a 'bundle' to send through the fragment the current restaurant object.*/
            val bundle = Bundle()
            bundle.putParcelable("restaurant", currentItem) /**The current restaurant*/
            bundle.putBoolean("visible", true) /**Visibility flag for the 'heart' icon*/

            /**Adding the bundle to the detailsFragment object arguments.*/
            val detailsPageFragment = DetailsPageFragment()
            detailsPageFragment.arguments = bundle

            /**Moving to the detailsPageFragment using transaction.*/
            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, detailsPageFragment)
            transaction.commit()
        }

        /**The heart logo, where the user can add a restaurant to his favorites.*/
        val hearth = holder.itemView.findViewById<ImageView>(R.id.ic_liked)
        hearth.setOnClickListener{
            /**When the user clicks on it, it will pop up a dialog box.*/
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setMessage("Are you sure you want to add to Favorites?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    daoViewModel.addRestaurantDB(currentItem)

                    /**Searching for the current restaurant in the current user's favorite.*/
                    var flag:Boolean = false
                    daoViewModel.readAllCross.observe(viewLifecycleO, {cross->

                        /**If the restaurant appear in the user's favorite, will not add it again.*/
                        for(v in cross){
                            if(v.id == currentItem.id && v.userID == Constants.user.userID){
                                flag = true
                                break
                            }
                        }

                        /**If the restaurant does not appear in the user's favorite, will add it.*/
                        if(!flag){
                            /**Generating the id for the junction table. */
                            val crossID: Int = Random().nextInt(100000)
                            /**Adding the restaurant into the database.*/
                            Constants.userList.add(currentItem)
                            /**Adding the restaurant and the current user into the database.(many-to-many relationship)*/
                            daoViewModel.addUserRestaurantDB(UserRestaurantCross(crossID, currentItem.id, Constants.user.userID ))
                            notifyDataSetChanged()
                            Toast.makeText(holder.itemView.context, "Item added to favorites!", Toast.LENGTH_SHORT).show()
                        }
                    })

                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create().show()
        }
    }

    /**RecyclerView calls this method to get the size of the data set.*/
    override fun getItemCount() = exampleList.size

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restNameView: TextView = itemView.findViewById(R.id.restaurantName)
        val restPhoneView: TextView = itemView.findViewById(R.id.restaurantPhone)
        val restPriceView: TextView = itemView.findViewById(R.id.restaurantPrice)
        var restImageView: ImageView = itemView.findViewById(R.id.foodImage)
    }

    /**Helper function to initialize the adapter with data.*/
    fun setData(restaurants: MutableList<Restaurant>){
        this.exampleList = restaurants
        notifyDataSetChanged()
    }
}