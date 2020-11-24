package com.example.wheretoeat.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheretoeat.Adapter.Adapter
import com.example.wheretoeat.R
import com.example.wheretoeat.ViewModel.DaoViewModel
import kotlinx.android.synthetic.main.fragment_favorit.view.*

class FavoritFragment : Fragment() {
    private lateinit var daoViewModel: DaoViewModel
    var adapter: Adapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_favorit, container, false)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)
        adapter = context?.let { Adapter(daoViewModel, it) }

        view.recycle_view_favorite.adapter = adapter
        view.recycle_view_favorite.layoutManager = LinearLayoutManager(requireContext())
        view.recycle_view_favorite.setHasFixedSize(true)

        return view
    }
}