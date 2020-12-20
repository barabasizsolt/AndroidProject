package com.example.wheretoeat.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheretoeat.Adapter.FavoriteAdapter
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.R
import com.example.wheretoeat.Util.Constants
import com.example.wheretoeat.ViewModel.DaoViewModel
import kotlinx.android.synthetic.main.fragment_favorit.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

/**Shows the current user favorite restaurant is a recyclerView.*/
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

        /**TextView for searching between the favorite items(search by price).*/
        val search = view.findViewById<TextView>(R.id.searchBarFavorite)

        /**Setting up the adapter and the viewModel for the recyclerView.*/
        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)
        adapter = context?.let { FavoriteAdapter(daoViewModel, it) }

        view.recycle_view_favorite.adapter = adapter
        view.recycle_view_favorite.layoutManager = LinearLayoutManager(requireContext())
        view.recycle_view_favorite.setHasFixedSize(true)

        adapter!!.setData(Constants.userList)
        /*------------------------------------------------------------------------------*/

        /**Searching by price between restaurants.*/
        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /**Lambda function -> filter the restaurants where the price is equal to the input price.*/
                if(s.toString().isNotEmpty()){
                    adapter?.setData(Constants.userList.filter { tmp->
                        tmp.price.toString().toLowerCase(Locale.ROOT).contains(s.toString().toLowerCase(Locale.ROOT)) } as MutableList<Restaurant>)
                }
                else{
                    adapter?.setData(Constants.userList)
                }
            }
        })

        return view
    }
}