package com.example.wheretoeat.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.example.wheretoeat.Adapter.CustomDropDownAdapter
import com.example.wheretoeat.Model.Logo
import com.example.wheretoeat.R
import com.example.wheretoeat.Util.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

/**Current user's profile.*/
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        /**Setting the navigation bar visible, when the user enter the ProfileFragment.*/
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNav)
        navBar.isVisible = true

        /**Setting up the user details's TextViews*/
        val profileName = view.findViewById<TextView>(R.id.profileName)
        val profileAddress = view.findViewById<TextView>(R.id.address)
        val profileEmail = view.findViewById<TextView>(R.id.email)
        val profilePhone = view.findViewById<TextView>(R.id.mobile)

        profileName.text = Constants.user.nickname
        profileAddress.text = Constants.user.address
        profileEmail.text = Constants.user.email
        profilePhone.text = Constants.user.mobile
        /*---------------------------------------------------------------------*/

        /**Setting up the 'userLogo' spinner*/
        /*--------------------------------------------------------------------------*/
        val profileLogo = view.findViewById<ImageView>(R.id.userLogo)

        val modelList: List<Logo> = readFromAsset()
        val customDropDownAdapter = context?.let { CustomDropDownAdapter(it, modelList) }
        val spinnerLogo = view.findViewById<Spinner>(R.id.spinnerLogo)
        if (spinnerLogo != null) {
            spinnerLogo.adapter = customDropDownAdapter
        }

        /**Changing the avatar every time the user select a new one.*/
        spinnerLogo?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ){
                /**The avatar drawable id is stored in the 'Constants.myImageList' */
                context?.let { Glide.with(it).load(Constants.myImageList[spinnerLogo.selectedItemPosition]).circleCrop().into(profileLogo)
                }
            }
        }
        /*--------------------------------------------------------------------------*/

        /**logOut button -> logging out, deleting the Constants.userList, making the bottomNavigation invisible. */
        val logOut = view.findViewById<Button>(R.id.logOut)
        logOut.setOnClickListener {
            navBar.isVisible = false
            Constants.userList.clear()

            /**Moving to the loginFragment.*/
            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, LoginFragment())
            transaction.commit()
        }
        /*--------------------------------------------------------------------------*/

        /**resetPwd button -> reset the current user's password. */
        val resetPwd =view.findViewById<Button>(R.id.resetPassword)
        resetPwd.setOnClickListener {
            /**Moving to the resetFragment*/
            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, ResetFragment())
            transaction.commit()
        }
        /*--------------------------------------------------------------------------*/

        return view
    }

    /**Helper function for my custom dropDownListAdapter.
     *The spinner values are loaded from a json file(logos.json/restaurant.json).*/
    private fun readFromAsset(): List<Logo> {
        val fileName = "logos.json"
        val bufferReader = activity?.assets?.open(fileName)?.bufferedReader()
        val jsonString = bufferReader.use { it?.readText() }
        val gson = Gson()
        return gson.fromJson(jsonString, Array<Logo>::class.java).toList()
    }
}