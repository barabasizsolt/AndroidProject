package com.example.wheretoeat.Fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheretoeat.Adapter.Adapter
import com.example.wheretoeat.MainActivity
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.R
import com.example.wheretoeat.ViewModel.DaoViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.util.*


class MainFragment : Fragment() {
    private lateinit var daoViewModel: DaoViewModel
    var adapter: Adapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)
        adapter = Adapter(daoViewModel)

        view.recycle_view.adapter = adapter
        view.recycle_view.layoutManager = LinearLayoutManager(requireContext())
        view.recycle_view.setHasFixedSize(true)

        daoViewModel.readAllData.observe(viewLifecycleOwner, Observer { restaurant ->
            adapter!!.setData(restaurant as MutableList<Restaurant>)
        })

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val menuItem = menu?.findItem(R.id.search)
        val searchView = menuItem?.actionView as SearchView

        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter?.filter(newText)
                return false
            }
        })
    }
}