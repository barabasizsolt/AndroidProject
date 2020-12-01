package com.example.wheretoeat.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.wheretoeat.Adapter.CustomDropDownAdapter
import com.example.wheretoeat.Model.Logo
import com.example.wheretoeat.R
import com.example.wheretoeat.Util.Constants
import com.google.gson.Gson


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

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

        return view
    }

    private fun readFromAsset(): List<Logo> {
        val fileName = "logos.json"
        val bufferReader = activity?.assets?.open(fileName)?.bufferedReader()
        val jsonString = bufferReader.use {
            it?.readText()
        }
        val gson = Gson()
        return gson.fromJson(jsonString, Array<Logo>::class.java).toList()
    }
}