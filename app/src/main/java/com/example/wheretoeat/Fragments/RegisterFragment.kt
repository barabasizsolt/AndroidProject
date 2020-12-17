package com.example.wheretoeat.Fragments

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wheretoeat.Model.User
import com.example.wheretoeat.R
import com.example.wheretoeat.Util.Constants
import com.example.wheretoeat.ViewModel.DaoViewModel
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class RegisterFragment : Fragment() {
    private lateinit var daoViewModel: DaoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

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
            if (name.text.toString().isNotEmpty() && pass.text.toString().isNotEmpty() && address.text.toString().isNotEmpty()
                && email.text.toString().isNotEmpty() && phone.text.toString().isNotEmpty()
            ) {

                val bundle = Bundle()

                val randomNumber: Int = java.util.Random().nextInt(100000)
                val user = User(
                    randomNumber,
                    name.text.toString(),
                    pass.text.toString(),
                    address.text.toString(),
                    email.text.toString(),
                    phone.text.toString()
                )

                val shr = runBlocking { daoViewModel.getUserEmailDB(user.email) }
                shr.observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        Toast.makeText(context, "This email is already exist!", Toast.LENGTH_SHORT).show()
                    } else {
                        daoViewModel.addUserDB(user)
                        Constants.user = user

                        daoViewModel.readAllUser.observe(viewLifecycleOwner, { tmp ->
                            Log.d("regUsr: ", tmp.toString())
                        })

                        Toast.makeText(context, "Registered!", Toast.LENGTH_SHORT).show()

                        val profilePageFragment = ProfileFragment()
                        profilePageFragment.arguments = bundle

                        val transaction =
                            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.nav_host_fragment, profilePageFragment)
                        transaction.commit()
                    }
                })
            } else {
                Toast.makeText(context, "Please fill out the fields!", Toast.LENGTH_SHORT)
                    .show()
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