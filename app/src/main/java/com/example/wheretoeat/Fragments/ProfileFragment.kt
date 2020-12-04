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


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNav)
        navBar.isVisible = true

        val profileName = view.findViewById<TextView>(R.id.profileName)
        val profileAddress = view.findViewById<TextView>(R.id.address)
        val profileEmail = view.findViewById<TextView>(R.id.email)
        val profilePhone = view.findViewById<TextView>(R.id.mobile)

        profileName.text = arguments?.getString("nickname")
        profileAddress.text = arguments?.getString("address")
        profileEmail.text = arguments?.getString("email")
        profilePhone.text = arguments?.getString("phone")

        val profileLogo = view.findViewById<ImageView>(R.id.userLogo)

        val modelList: List<Logo> = readFromAsset()
        val elem = modelList[0]
        val customDropDownAdapter = context?.let { CustomDropDownAdapter(it, modelList) }

        val spinnerLogo = view.findViewById<Spinner>(R.id.spinnerLogo)
        if (spinnerLogo != null) {
            spinnerLogo.adapter = customDropDownAdapter
        }

        spinnerLogo?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ){
                context?.let { Glide.with(it).load(Constants.myImageList[spinnerLogo.selectedItemPosition]).
                circleCrop().into(profileLogo)
                }
            }
        }

        val logOut = view.findViewById<Button>(R.id.logOut)
        logOut.setOnClickListener {
            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, LoginFragment())
            transaction.commit()
        }

        return view
    }

    private fun readFromAsset(): List<Logo> {
        val fileName = "logos.json"
        val bufferReader = activity?.assets?.open(fileName)?.bufferedReader()
        val jsonString = bufferReader.use { it?.readText() }
        val gson = Gson()
        return gson.fromJson(jsonString, Array<Logo>::class.java).toList()
    }
}