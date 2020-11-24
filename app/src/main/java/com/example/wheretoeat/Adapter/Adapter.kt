package com.example.wheretoeat.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.Fragments.DetailsPageFragment
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.R
import com.example.wheretoeat.ViewModel.DaoViewModel
import java.util.*
import kotlin.collections.ArrayList


class Adapter(private var daoViewModel: DaoViewModel, mContext: Context) : RecyclerView.Adapter<Adapter.FoodViewHolder>(), Filterable {
    private var exampleList: MutableList<Restaurant> = mutableListOf()
    private var exampleListFull: MutableList<Restaurant> = mutableListOf()
    private  var context = mContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycle_element,
            parent, false
        )
        return FoodViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.restNameView.text = currentItem.name
        holder.restPhoneView.text = currentItem.phone
        holder.restPriceView.text = currentItem.price.toString()

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name", currentItem.name)
            bundle.putString("phone", currentItem.phone)
            bundle.putString("price", currentItem.price.toString())

            val detailsPageFragment = DetailsPageFragment()
            detailsPageFragment.arguments = bundle

            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, detailsPageFragment)
            transaction.commit()
        }
    }

    override fun getItemCount() = exampleList.size

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restNameView: TextView = itemView.findViewById(R.id.restaurantName)
        val restPhoneView: TextView = itemView.findViewById(R.id.restaurantPhone)
        val restPriceView: TextView = itemView.findViewById(R.id.restaurantPrice)
    }

    fun setData(restaurants: MutableList<Restaurant>){
        this.exampleList = restaurants
        this.exampleListFull = restaurants
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter? {
        return exampleFilter
    }

    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<Restaurant> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(exampleListFull)
            } else {
                val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
                for (item in exampleListFull) {
                    if (item.name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            exampleList.clear()
            exampleList.addAll(results.values as List<Restaurant>)
            notifyDataSetChanged()
        }
    }
}