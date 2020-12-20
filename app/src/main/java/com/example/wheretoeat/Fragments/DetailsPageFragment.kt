package com.example.wheretoeat.Fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

/**Shows details about one restaurant.*/
class DetailsPageFragment : Fragment() {
    private lateinit var daoViewModel: DaoViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details_page, container, false)

        /**Creating a viewModel.*/
        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

        /**Getting a restaurant object MainFragment/FavoriteFragment.
         * Restaurant needs to be Parcelable.
         * isV = the visibility of the heart logo(invisible in FavoriteFragment).
         * */
        val restaurant = arguments?.getParcelable<Restaurant>("restaurant")!!
        val isV = arguments?.getBoolean("visible")

        /**Setting up the textViews(name, city, address, country, postal code).*/
        val name = view.findViewById<TextView>(R.id.detailsName)
        name.text = "Name:  ${restaurant.name}"
        val city = view.findViewById<TextView>(R.id.detailsCity)
        city.text = "City: ${restaurant.city}"
        val address = view.findViewById<TextView>(R.id.detailsAddress)
        address.text = "Address: ${restaurant.address}"
        val country = view.findViewById<TextView>(R.id.detailsCountry)
        country.text = "Country: ${restaurant.country}"
        val postal = view.findViewById<TextView>(R.id.detailsPostal)
        postal.text = "Postal Code: ${restaurant.postal_code}"

        /**The imageView as default has 'foodimage4' as value.*/
        val image = view.findViewById<ImageView>(R.id.imageURL)
        Glide.with(this).load(R.drawable.foodimage4).into(image)

        /**Creating a custom adapter to change between images(inside, outside, menu).*/
        val modelList: List<Logo> = readFromAsset()
        val customDropDownAdapter = context?.let { CustomDropDownAdapter(it, modelList) }
        val spinnerRestImg = view.findViewById<Spinner>(R.id.spinnerRestImage)

        /**Making a spinner with customDropDownAdapter.*/
        if (spinnerRestImg != null) {
            spinnerRestImg.adapter = customDropDownAdapter
        }

        /**Changing the imageView every time, when a new image is selected from the spinner.*/
        spinnerRestImg?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ){
                /**Setting up the new image, which id's is stored in the 'Util package/Constants class.'*/
                context?.let { Glide.with(it).load(Constants.myImageRest[spinnerRestImg.selectedItemPosition]).into(image) }
            }
        }

        /**Similar hearth logo to the Adapter's hearth logo, with the same functionality.*/
        val hearth = view.findViewById<ImageView>(R.id.logoFavorite)
        /**Setting up the hearth visibility(invisible in the FavoriteAdapter).*/
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
                            val crossID: Int = Random().nextInt(100000)
                            Constants.userList.add(restaurant)
                            daoViewModel.addUserRestaurantDB(UserRestaurantCross(crossID, restaurant.id, Constants.user.userID ))
                            Toast.makeText(context, "Item added to favorites!", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create().show()
        }

        /**mapButton -> navigate to the google maps and find the restaurant using his coordinates.*/
        /*---------------------------------------------------------------------------*/
        val mapButton = view.findViewById<Button>(R.id.mapButton)
        mapButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW

            /**Coordinates: latitude and longitude.*/
            intent.data = Uri.parse("geo:${restaurant.lat},${restaurant.lng}")
            startActivity(intent)
        }
        /*---------------------------------------------------------------------------*/

        /**callButton -> with this button the user can call up the restaurant.*/
        val callButton = view.findViewById<Button>(R.id.callButton)
        callButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_DIAL

            /**Calling the restaurant.*/
            intent.data = Uri.parse("tel:" + restaurant.phone)
            startActivity(intent)
        }
        /*---------------------------------------------------------------------------*/

        /**callButton -> with this button the user can visit the original(OpenTable) site of the restaurant.*/
        val visitButton = view.findViewById<Button>(R.id.visitButton)
        visitButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW

            /**Navigating to the given URL.*/
            intent.data = Uri.parse(restaurant.reserve_url)
            startActivity(intent)
        }
        /*---------------------------------------------------------------------------*/

        return view
    }

    /**Helper function for my custom dropDownListAdapter.
     *The spinner values are loaded from a json file(logos.json/restaurant.json).*/
    private fun readFromAsset(): List<Logo> {
        val fileName = "restaurant.json"
        val bufferReader = activity?.assets?.open(fileName)?.bufferedReader()
        val jsonString = bufferReader.use { it?.readText() }
        val gson = Gson()
        return gson.fromJson(jsonString, Array<Logo>::class.java).toList()
    }
}