package com.example.wheretoeat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.wheretoeat.Adapter.CustomDropDownAdapter
import com.example.wheretoeat.Fragments.FavoriteFragment
import com.example.wheretoeat.Fragments.LoginFragment
import com.example.wheretoeat.Fragments.MainFragment
import com.example.wheretoeat.Fragments.ProfileFragment
import com.example.wheretoeat.Model.Logo
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*

class MainActivity : AppCompatActivity() {
    //private lateinit var daoViewModel: DaoViewModel
    private val mainFragment = MainFragment()
    private val profileFragment = ProfileFragment()
    private val favoriteFragment = FavoriteFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_main)

        bottomNav.isVisible = false

        bottomNav.setOnNavigationItemSelectedListener {
            var selectedFragment: Fragment = LoginFragment()
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
}