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
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.R
import com.example.wheretoeat.ViewModel.DaoViewModel
import kotlinx.android.synthetic.main.fragment_favorit.view.*
import kotlinx.android.synthetic.main.fragment_main.view.*


class MainFragment : Fragment() {
    private lateinit var daoViewModel: DaoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

        val adapter = Adapter(daoViewModel)

        view.recycle_view.adapter = adapter
        view.recycle_view.layoutManager = LinearLayoutManager(requireContext())
        view.recycle_view.setHasFixedSize(true)

        daoViewModel.readAllData.observe(viewLifecycleOwner, Observer { restaurant ->
            adapter.setData(restaurant as MutableList<Restaurant>)
        })

        return view
    }

}