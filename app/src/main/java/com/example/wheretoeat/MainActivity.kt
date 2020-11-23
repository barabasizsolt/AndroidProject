package com.example.wheretoeat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wheretoeat.Adapter.Adapter
import com.example.wheretoeat.Fragments.FavoritFragment
import com.example.wheretoeat.Fragments.MainFragment
import com.example.wheretoeat.Fragments.ProfileFragment
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.ViewModel.DaoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var daoViewModel: DaoViewModel
    private val mainFragment = MainFragment()
    private val profileFragment = ProfileFragment()
    private val favoriteFragment = FavoritFragment()

    var adapter: Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)
        adapter = Adapter(daoViewModel)

        bottomNav.setOnNavigationItemSelectedListener {
            var selectedFragment: Fragment = ProfileFragment()
            when (it.itemId) {
                R.id.restaurant_main -> selectedFragment = mainFragment
                R.id.restaurant_profile -> selectedFragment = profileFragment
                R.id.restaurant_favorite -> selectedFragment = favoriteFragment
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, selectedFragment)
            transaction.commit()
            return@setOnNavigationItemSelectedListener true
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu, menu)
//        val menuItem = menu?.findItem(R.id.search)
//        val searchView = menuItem?.actionView as SearchView
//
//        searchView.maxWidth = Int.MAX_VALUE
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                adapter?.filter?.filter(newText)
//                return false
//            }
//        })
//        return true
//    }
}