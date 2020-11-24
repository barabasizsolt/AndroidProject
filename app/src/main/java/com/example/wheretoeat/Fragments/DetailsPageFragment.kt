package com.example.wheretoeat.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.wheretoeat.R


class DetailsPageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details_page, container, false)

        val name = view.findViewById<TextView>(R.id.detailsName)
        name.text = arguments?.getString("name")
        val phone = view.findViewById<TextView>(R.id.detailsPhone)
        phone.text = arguments?.getString("phone")
        val price = view.findViewById<TextView>(R.id.detailsPrice)
        price.text = arguments?.getString("price")

        return view
    }

}