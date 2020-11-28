package com.example.wheretoeat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.wheretoeat.Fragments.FavoritFragment
import com.example.wheretoeat.Fragments.MainFragment
import com.example.wheretoeat.Fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //private lateinit var daoViewModel: DaoViewModel
    private val mainFragment = MainFragment()
    private val profileFragment = ProfileFragment()
    private val favoriteFragment = FavoritFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_main)

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
}