package com.example.wheretoeat.Fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.UserRestaurantCross
import com.example.wheretoeat.R
import com.example.wheretoeat.Util.Constants
import com.example.wheretoeat.ViewModel.DaoViewModel
import kotlinx.coroutines.runBlocking


class DetailsPageFragment : Fragment() {
    private lateinit var daoViewModel: DaoViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details_page, container, false)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

        val restaurant = arguments?.getParcelable<Restaurant>("restaurant")!!
        val isV = arguments?.getBoolean("visible")
        Log.d("rest: ", restaurant.toString())

        val name = view.findViewById<TextView>(R.id.detailsName)
        name.text = "Name: " + restaurant.name
        val city = view.findViewById<TextView>(R.id.detailsCity)
        city.text = "City: " + restaurant.city
        val address = view.findViewById<TextView>(R.id.detailsAddress)
        address.text = "Address: " + restaurant.address


        val image = view.findViewById<ImageView>(R.id.imageURL)
        Glide.with(this).load(restaurant.image_url).into(image)

        val hearth = view.findViewById<ImageView>(R.id.logoFavorite)
        if (isV != null) hearth.isVisible = isV
        hearth.setOnClickListener{
            val builder = AlertDialog.Builder(view.context)
            builder.setMessage("Are you sure you want to add to Favorites?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    daoViewModel.addRestaurantDB(restaurant)
                    daoViewModel.addUserRestaurantDB(UserRestaurantCross(Constants.commonCrossID++, restaurant.id, Constants.user.userID))

//                    runBlocking { daoViewModel.addRestaurantDB(restaurant)}
//                    runBlocking { daoViewModel.addUserRestaurantDB(UserRestaurantCross(Constants.commonCrossID++, restaurant.id, Constants.user.userID))}

                    Toast.makeText(context, "Item added to favorites!", Toast.LENGTH_SHORT)
                        .show()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            builder.create().show()
        }

        val mapButton = view.findViewById<Button>(R.id.mapButton)
        mapButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            val lat = arguments?.getString("lat")
            val lng = arguments?.getString("lng")

            intent.data = Uri.parse("geo:${restaurant.lat},${restaurant.lng}")
            startActivity(intent)
        }

        val callButton = view.findViewById<Button>(R.id.callButton)
        callButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_DIAL

            intent.data = Uri.parse("tel:" + restaurant.phone)
            startActivity(intent)
        }

        val visitButton = view.findViewById<Button>(R.id.visitButton)
        visitButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW

            intent.data = Uri.parse(restaurant.reserve_url)
            startActivity(intent)
        }

        return view
    }
}