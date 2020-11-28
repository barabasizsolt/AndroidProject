package com.example.wheretoeat.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.wheretoeat.R
import kotlinx.android.synthetic.main.fragment_details_page.*


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

        val mapButton = view.findViewById<Button>(R.id.mapButton)
        mapButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            val lat = arguments?.getString("lat")
            val lng = arguments?.getString("lng")

            intent.data = Uri.parse("geo:$lat,$lng")
            startActivity(intent)
        }

        return view
    }
}