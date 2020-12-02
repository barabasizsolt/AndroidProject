package com.example.wheretoeat.Fragments

import android.R.attr.password
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.wheretoeat.R


class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val name = view.findViewById<TextView>(R.id.editTextTextPersonName)
        val pass = view.findViewById<TextView>(R.id.editTextTextPassword)
        pass.transformationMethod = PasswordTransformationMethod()

//        bottomNav.setOnNavigationItemSelectedListener{
//            it.isVisible = false
//            return@setOnNavigationItemSelectedListener true
//        }

        val login = view.findViewById<Button>(R.id.loginButton)
        login.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("nickname", name.text.toString())
            bundle.putString("password", pass.text.toString())

            val profilePageFragment = ProfileFragment()
            profilePageFragment.arguments = bundle

            if(name.text.toString().isNotEmpty() && pass.text.toString().isNotEmpty()) {
                val transaction =
                    (context as FragmentActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment, profilePageFragment)
                transaction.commit()
            }
            else{
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

            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, registerPageFragment)
            transaction.commit()
        }

        return view
    }
}