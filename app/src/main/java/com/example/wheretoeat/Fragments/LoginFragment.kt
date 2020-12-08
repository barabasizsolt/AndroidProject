package com.example.wheretoeat.Fragments

import android.R.attr.password
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wheretoeat.Model.Restaurant
import com.example.wheretoeat.R
import com.example.wheretoeat.Util.Constants
import com.example.wheretoeat.ViewModel.DaoViewModel
import kotlinx.coroutines.runBlocking


class LoginFragment : Fragment() {
    private lateinit var daoViewModel: DaoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

//        runBlocking {  daoViewModel.deleteAll()}
//        runBlocking {  daoViewModel.deleteAllUserDB()}
//        runBlocking {  daoViewModel.deleteAllCrossDB()}

        val name = view.findViewById<TextView>(R.id.editTextTextPersonName)
        val pass = view.findViewById<TextView>(R.id.editTextTextPassword)
        pass.transformationMethod = PasswordTransformationMethod()

        val login = view.findViewById<Button>(R.id.loginButton)
        login.setOnClickListener {
            if (name.text.toString().isNotEmpty() && pass.text.toString().isNotEmpty()) {
                val bundle = Bundle()

                val shr = runBlocking { daoViewModel.getUserDB(name.text.toString()) }
                shr.observe(viewLifecycleOwner, {
                    if (it == null || it.password != pass.text.toString()) {
                        Toast.makeText(context, "Wrong nickname or password!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Log.d("LOGIN", it.nickname)
                        Constants.user = it


                        val chr = runBlocking { daoViewModel.getUserWithRestaurant(it.userID) }
                        Log.d("chr", chr.toString())

                        chr.observe(viewLifecycleOwner, { ls ->
                            for (vh in ls) {
                                Log.d("REST", vh.restaurants.toString())
                                Constants.userLs.addAll(vh.restaurants)
                            }
                        })

                        Toast.makeText(context, "Welcome ${name.text}!", Toast.LENGTH_SHORT).show()

                        val profilePageFragment = ProfileFragment()
                        profilePageFragment.arguments = bundle

                        val transaction =
                            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.nav_host_fragment, profilePageFragment)
                        transaction.commit()
                    }
                })
            } else {
                Toast.makeText(context, "Please fill out the fields!", Toast.LENGTH_SHORT).show()
            }
        }

        val register = view.findViewById<Button>(R.id.registerButton)
        register.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("nicknameReg", name.text.toString())
            bundle.putString("passwordReg", pass.text.toString())

            val registerPageFragment = RegisterFragment()
            registerPageFragment.arguments = bundle

            val transaction =
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, registerPageFragment)
            transaction.commit()
        }

        return view
    }
}