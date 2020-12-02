package com.example.wheretoeat.Fragments

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.wheretoeat.R

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val name = view.findViewById<TextView>(R.id.regTextTextPersonName)
        val pass = view.findViewById<TextView>(R.id.regTextTextPassword)
        val address = view.findViewById<TextView>(R.id.regTextAddress)
        val email = view.findViewById<TextView>(R.id.regTextTextEmail)
        val phone = view.findViewById<TextView>(R.id.regTextPhone)


        pass.transformationMethod = PasswordTransformationMethod()

        name.text = arguments?.getString("nicknameReg")
        pass.text = arguments?.getString("passwordReg")

        val register = view.findViewById<Button>(R.id.regButton)
        register.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("nickname", name.text.toString())
            bundle.putString("password", pass.text.toString())
            bundle.putString("address", address.text.toString())
            bundle.putString("email", email.text.toString())
            bundle.putString("phone", phone.text.toString())

            val profilePageFragment = ProfileFragment()
            profilePageFragment.arguments = bundle

            if(name.text.toString().isNotEmpty() && pass.text.toString().isNotEmpty() && address.text.toString().isNotEmpty()
                && email.text.toString().isNotEmpty() && phone.text.toString().isNotEmpty()) {
                val transaction =
                    (context as FragmentActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment, profilePageFragment)
                transaction.commit()
            }
            else{
                Toast.makeText(context, "Please fill out the fields!", Toast.LENGTH_SHORT).show()
            }
        }

        val back = view.findViewById<Button>(R.id.backToLogin)
        back.setOnClickListener {
            val transaction =
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, LoginFragment())
            transaction.commit()
        }

        return view
    }
}