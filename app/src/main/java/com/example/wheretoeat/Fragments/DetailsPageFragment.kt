package com.example.wheretoeat.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
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
        val city = view.findViewById<TextView>(R.id.detailsCity)
        city.text = arguments?.getString("city")

        val image = view.findViewById<ImageView>(R.id.imageURL)
        Glide.with(this).load(arguments?.getString("image")).into(image)

        val mapButton = view.findViewById<Button>(R.id.mapButton)
        mapButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            val lat = arguments?.getString("lat")
            val lng = arguments?.getString("lng")

            intent.data = Uri.parse("geo:$lat,$lng")
            startActivity(intent)
        }

        val callButton = view.findViewById<Button>(R.id.callButton)
        callButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_DIAL

            intent.data = Uri.parse("tel:" + (arguments?.getString("phone")))
            startActivity(intent)
        }

        val visitButton = view.findViewById<Button>(R.id.visitButton)
        visitButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW

            intent.data = Uri.parse(arguments?.getString("home"))
            startActivity(intent)
        }

        return view
    }
}