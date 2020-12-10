package com.example.wheretoeat.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheretoeat.Adapter.Adapter
import com.example.wheretoeat.Adapter.FavoriteAdapter
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.Model.User
import com.example.wheretoeat.Model.UserRestaurantCross
import com.example.wheretoeat.Model.UserWithRestaurant
import com.example.wheretoeat.R
import com.example.wheretoeat.Util.Constants
import com.example.wheretoeat.ViewModel.DaoViewModel
import kotlinx.android.synthetic.main.fragment_favorit.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking

class FavoriteFragment : Fragment() {
    private lateinit var daoViewModel: DaoViewModel
    var adapter: FavoriteAdapter? = null

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorit, container, false)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)
        adapter = context?.let { FavoriteAdapter(daoViewModel, it) }

//        Constants.userLs.clear()
//
//        runBlocking {
//            daoViewModel.getUserWithRestaurant(Constants.user.userID).observe(
//                viewLifecycleOwner, Observer { ls ->
//                    for (vh in ls) {
//                        Log.d("REST", vh.restaurants.toString())
//                        Constants.userLs.addAll(vh.restaurants)
//                    }
//                }
//            )
//        }

        view.recycle_view_favorite.adapter = adapter
        view.recycle_view_favorite.layoutManager = LinearLayoutManager(requireContext())
        view.recycle_view_favorite.setHasFixedSize(true)

//        daoViewModel.readAllData.observe(viewLifecycleOwner, Observer { restaurant ->
//            adapter!!.setData(restaurant as MutableList<Restaurant>)
//        })

        adapter!!.setData(Constants.userLs)

        return view
    }
}