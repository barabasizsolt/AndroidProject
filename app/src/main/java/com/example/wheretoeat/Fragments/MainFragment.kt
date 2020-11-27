package com.example.wheretoeat.Fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheretoeat.Adapter.Adapter
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.R
import com.example.wheretoeat.Repository.Repository
import com.example.wheretoeat.Splash.SplashActivity
import com.example.wheretoeat.Util.Constants
import com.example.wheretoeat.ViewModel.DaoViewModel
import com.example.wheretoeat.ViewModel.MainRestaurantViewModelFactory
import com.example.wheretoeat.ViewModel.RestaurantViewModel
import kotlinx.android.synthetic.main.fragment_main.view.*


class MainFragment : Fragment() {
    private lateinit var daoViewModel: DaoViewModel
    private lateinit var viewModel: RestaurantViewModel
    var adapter: Adapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val spinner = view.findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter1 =
                activity?.let {
                    ArrayAdapter(
                        it,
                        android.R.layout.simple_spinner_item,
                        Constants.countries
                    )
                }
            spinner.adapter = adapter1
        }

        val numbers = listOf<Int>(1,2,3,4,5,6,7,8,9,10)

        val spinnerPage = view.findViewById<Spinner>(R.id.spinnerPage)
        if (spinnerPage != null) {
            val adapter2 =
                activity?.let {
                    ArrayAdapter(
                        it,
                        android.R.layout.simple_spinner_item,
                        numbers
                    )
                }
            spinnerPage.adapter = adapter2
        }

        Log.d("States3: ", Constants.countries.toString())

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)
        adapter = context?.let { Adapter(daoViewModel, it) }

        view.recycle_view.adapter = adapter
        view.recycle_view.layoutManager = LinearLayoutManager(requireContext())
        view.recycle_view.setHasFixedSize(true)

        var L: ArrayList<Restaurant> = arrayListOf()

        val repository = Repository()
        val viewModelFactory = MainRestaurantViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RestaurantViewModel::class.java)

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                spinnerPage?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    }
                }

                viewModel.getAllRestaurant(spinner.selectedItem.toString(), 1)
                viewModel.myResponseAll.observe(viewLifecycleOwner, Observer { response1 ->
                    if (response1.isSuccessful) {
                        Log.d("Current country", spinner.selectedItem.toString())

                        if(response1.body()?.restaurants!!.isNotEmpty()) {
                            L = response1.body()?.restaurants!!
                        }
                        Log.d("L size: ", L.size.toString())
                        adapter?.setData(L)
                    }
                })
            }
        }

        return view
    }
}