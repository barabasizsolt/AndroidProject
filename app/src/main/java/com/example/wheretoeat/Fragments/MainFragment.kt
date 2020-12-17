package com.example.wheretoeat.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheretoeat.Adapter.Adapter
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.R
import com.example.wheretoeat.Repository.Repository
import com.example.wheretoeat.Util.Constants
import com.example.wheretoeat.ViewModel.DaoViewModel
import com.example.wheretoeat.ViewModel.MainRestaurantViewModelFactory
import com.example.wheretoeat.ViewModel.RestaurantViewModel
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.util.*
import kotlin.collections.ArrayList


class MainFragment : Fragment() {
    private lateinit var daoViewModel: DaoViewModel
    private lateinit var viewModel: RestaurantViewModel
    private var adapter: Adapter? = null
    private var mySearch: CharSequence = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val search = view.findViewById<TextView>(R.id.search_bar)

        val spinner = view.findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter =
                activity?.let {
                    ArrayAdapter(it, android.R.layout.simple_spinner_item, Constants.cities
                    )
                }
            spinner.adapter = adapter
        }

        val pageNum = (1..100).toList()
        val spinnerPage = view.findViewById<Spinner>(R.id.spinnerPage)
        if (spinnerPage != null) {
            val adapter =
                activity?.let {
                    ArrayAdapter(it, android.R.layout.simple_spinner_item, pageNum
                    )
                }
            spinnerPage.adapter = adapter
        }

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)
        adapter = context?.let { Adapter(daoViewModel, it, viewLifecycleOwner) }

        view.recycle_view.adapter = adapter
        view.recycle_view.layoutManager = LinearLayoutManager(requireContext())
        view.recycle_view.setHasFixedSize(true)

        var L: ArrayList<Restaurant> = arrayListOf()

        val repository = Repository()
        val viewModelFactory = MainRestaurantViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RestaurantViewModel::class.java)

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ){
                viewModel.getAllRestaurant(spinner.selectedItem.toString(),  spinnerPage.selectedItem as Int)
                Log.d("rest", viewModel.myResponseAll.value.toString())
                search.text = ""

                spinnerPage?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ){
                        viewModel.getAllRestaurant(spinner.selectedItem.toString(),  spinnerPage.selectedItem as Int)
                        Log.d("rest", viewModel.myResponseAll.value.toString())
                        search.text = ""
                    }
                }
            }
        }

        viewModel.myResponseAll.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                Log.d("Current country", spinner.selectedItem.toString())
                Log.d("Current page", spinnerPage.selectedItem.toString())

                if (response.body()?.restaurants!!.isNotEmpty()) {
                    L = response.body()?.restaurants!!
                    adapter?.setData(L)
                }
                else{
                    Toast.makeText(context, "Not existing page!", Toast.LENGTH_SHORT).show()
                    adapter?.setData( mutableListOf())
                }
                Log.d("L size: ", L.size.toString())

            }
        })

        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().isNotEmpty()){
                    adapter?.setData(L.filter {
                        it.price.toString().toLowerCase(Locale.ROOT).contains(s.toString().toLowerCase(Locale.ROOT))
                    } as MutableList<Restaurant>)
                }
                else{
                    adapter?.setData(L)
                }
            }
        })

        return view
    }
}