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
import com.example.wheretoeat.R
import com.example.wheretoeat.Util.Constants
import com.example.wheretoeat.ViewModel.DaoViewModel
import kotlinx.android.synthetic.main.fragment_favorit.view.*

class FavoriteFragment : Fragment() {
    private lateinit var daoViewModel: DaoViewModel
    var adapter: FavoriteAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_favorit, container, false)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)
        adapter = context?.let { FavoriteAdapter(daoViewModel, it) }

        view.recycle_view_favorite.adapter = adapter
        view.recycle_view_favorite.layoutManager = LinearLayoutManager(requireContext())
        view.recycle_view_favorite.setHasFixedSize(true)

        daoViewModel.readAllData.observe(viewLifecycleOwner, Observer { restaurant ->
            adapter!!.setData(restaurant as MutableList<Restaurant>)
        })

        //Log.d("Pages: ", Constants.pages.toString())

        val delete = view.findViewById<ImageView>(R.id.allDelete)
        delete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to Delete Everything?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    daoViewModel.deleteAll()
                    Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            builder.create().show()
        }

//        val user = User(1, "xx", "xx", "xx", "xx", "xx")
//        daoViewModel.addUserDB(user)

        return view
    }
}