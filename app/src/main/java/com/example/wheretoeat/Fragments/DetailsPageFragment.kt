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
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.wheretoeat.Adapter.CustomDropDownAdapter
import com.example.wheretoeat.Model.Logo
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.UserRestaurantCross
import com.example.wheretoeat.R
import com.example.wheretoeat.Util.Constants
import com.example.wheretoeat.ViewModel.DaoViewModel
import com.google.gson.Gson
import java.util.*

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
        name.text = restaurant.name
        val city = view.findViewById<TextView>(R.id.detailsCity)
        city.text = restaurant.city
        val address = view.findViewById<TextView>(R.id.detailsAddress)
        address.text = restaurant.address

        val image = view.findViewById<ImageView>(R.id.imageURL)
        Glide.with(this).load(R.drawable.foodimage4).into(image)

        val modelList: List<Logo> = readFromAsset()
        val customDropDownAdapter = context?.let { CustomDropDownAdapter(it, modelList) }
        val spinnerRestImg = view.findViewById<Spinner>(R.id.spinnerRestImage)

        if (spinnerRestImg != null) {
            spinnerRestImg.adapter = customDropDownAdapter
        }

        spinnerRestImg?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ){
                context?.let { Glide.with(it).load(Constants.myImageRest[spinnerRestImg.selectedItemPosition]).into(image) }
            }
        }

        val hearth = view.findViewById<ImageView>(R.id.logoFavorite)
        hearth.isVisible = isV!!
        hearth.setOnClickListener{
            val builder = AlertDialog.Builder(view.context)
            builder.setMessage("Are you sure you want to add to Favorites?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    daoViewModel.addRestaurantDB(restaurant)

                    var flag:Boolean = false
                    daoViewModel.readAllCross.observe(viewLifecycleOwner, {cross->

                        for(v in cross){
                            if(v.id == restaurant.id && v.userID == Constants.user.userID){
                                flag = true
                                break
                            }
                        }

                        if(!flag){
                            val randomNumber: Int = Random().nextInt(100000)
                            Constants.userLs.add(restaurant)
                            daoViewModel.addUserRestaurantDB(UserRestaurantCross(randomNumber, restaurant.id, Constants.user.userID ))
                            Toast.makeText(context, "Item added to favorites!", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create().show()
        }

        val mapButton = view.findViewById<Button>(R.id.mapButton)
        mapButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW

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

    private fun readFromAsset(): List<Logo> {
        val fileName = "restaurant.json"
        val bufferReader = activity?.assets?.open(fileName)?.bufferedReader()
        val jsonString = bufferReader.use { it?.readText() }
        val gson = Gson()
        return gson.fromJson(jsonString, Array<Logo>::class.java).toList()
    }
}