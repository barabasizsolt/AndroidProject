package com.example.wheretoeat.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait
import java.util.*

/**Loading dates from the API.*/
class MainFragment : Fragment() {
    private lateinit var daoViewModel: DaoViewModel
    private lateinit var viewModel: RestaurantViewModel
    private var adapter: Adapter? = null
    private var mySearch: CharSequence = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        /**TextView for searching between the favorite items(search by price).*/
        val search = view.findViewById<TextView>(R.id.search_bar)

        /**Setting up the spinners*/
        /*----------------------------------------------------------------*/
        /**Spinner to choose between cities*/
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter =
                activity?.let {
                    /**Constants.cities are loaded during the SplashScreen activity.*/
                    ArrayAdapter(it, android.R.layout.simple_spinner_item, Constants.cities)
                }
            spinner.adapter = adapter
        }

        /**Spinner to choose between pages*/
        val pageNum = (1..50).toList()
        val spinnerPage = view.findViewById<Spinner>(R.id.spinnerPage)
        if (spinnerPage != null) {
            val adapter =
                activity?.let {
                    ArrayAdapter(it, android.R.layout.simple_spinner_item, pageNum)
                }
            spinnerPage.adapter = adapter
        }
        /*----------------------------------------------------------------*/

        /**Setting up the adapter and the viewModel for the recyclerView/database */
        /*-----------------------------------------------------------------------*/
        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)
        adapter = context?.let { Adapter(daoViewModel, it, viewLifecycleOwner) }

        view.recycle_view.adapter = adapter
        view.recycle_view.layoutManager = LinearLayoutManager(requireContext())
        view.recycle_view.setHasFixedSize(true)

        var mainList: ArrayList<Restaurant> = arrayListOf()
        /*-----------------------------------------------------------------------*/

        /**Setting up the repository for the API*/
        val repository = Repository()
        val viewModelFactory = MainRestaurantViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RestaurantViewModel::class.java)
        /*-----------------------------------------------------------------------------------------------*/

        /**Filtering the restaurants(pagination) by cities and pages*/
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
                /**Filter by city(is the page is not selected).*/
                viewModel.getAllRestaurant(spinner.selectedItem.toString(), spinnerPage.selectedItem as Int)
                search.text = ""

                /**Filter by city and page.*/
                spinnerPage?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
                        viewModel.getAllRestaurant(
                            spinner.selectedItem.toString(),
                            spinnerPage.selectedItem as Int
                        )
                        search.text = ""
                    }
                }
            }
        }
        /*---------------------------------------------------------------------------------------------------------*/

        /**Loading the restaurant(after filter) into the recyclerView.*/
        viewModel.myResponseAll.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                if (response.body()?.restaurants!!.isNotEmpty()) {
                    mainList = response.body()?.restaurants!!
                    adapter?.setData(mainList)
                } else {
                    Toast.makeText(context, "Not existing page!", Toast.LENGTH_SHORT).show()
                    adapter?.setData(mutableListOf())
                }
            }
        })

        /**Searching by price between restaurants.*/
        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    /**Lambda function -> filter the restaurants where the price is equal to the input price.*/
                    adapter?.setData(mainList.filter {
                        it.price.toString().toLowerCase(Locale.ROOT).contains(s.toString().toLowerCase(Locale.ROOT))
                    } as MutableList<Restaurant>)

                } else {
                    adapter?.setData(mainList)
                }
            }
        })

        return view
    }
}