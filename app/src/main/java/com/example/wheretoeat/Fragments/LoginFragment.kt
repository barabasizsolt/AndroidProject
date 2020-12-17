package com.example.wheretoeat.Fragments

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
import androidx.lifecycle.ViewModelProvider
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

        val name = view.findViewById<TextView>(R.id.editTextTextPersonName)
        val pass = view.findViewById<TextView>(R.id.editTextTextPassword)
        pass.transformationMethod = PasswordTransformationMethod()

        runBlocking {
            daoViewModel.readAllCross.observe(viewLifecycleOwner, {cr->
                runBlocking {
                    daoViewModel.readAllUser.observe(viewLifecycleOwner,{ls->
                        runBlocking {
                            daoViewModel.readAllData.observe(viewLifecycleOwner, {rs->

                                val login = view.findViewById<Button>(R.id.loginButton)
                                login.setOnClickListener {
                                    if (name.text.toString().isNotEmpty() && pass.text.toString().isNotEmpty()) {
                                        val bundle = Bundle()

                                        runBlocking {
                                            daoViewModel.getUserDB(name.text.toString()).observe(viewLifecycleOwner, {usr->
                                                if (usr == null || usr.password != pass.text.toString()) {
                                                    Toast.makeText(context, "Wrong nickname or password!", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    Log.d("LOGIN", usr.nickname)
                                                    Constants.user = usr

                                                    for(v in cr){
                                                        if(v.userID == usr.userID){
                                                            for(w in rs){
                                                                if(v.id == w.id){
                                                                    Constants.userLs.add(w)
                                                                    Log.d("userRest: ", w.toString())
                                                                }
                                                            }
                                                        }
                                                    }

                                                    Toast.makeText(context, "Welcome ${name.text}!", Toast.LENGTH_SHORT).show()

                                                    val profilePageFragment = ProfileFragment()
                                                    profilePageFragment.arguments = bundle

                                                    val transaction =
                                                        (context as FragmentActivity).supportFragmentManager.beginTransaction()
                                                    transaction.replace(R.id.nav_host_fragment, profilePageFragment)
                                                    transaction.commit()
                                                }
                                            })
                                        }
                                    } else {
                                        Toast.makeText(context, "Please fill out the fields!", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        }
                    })
                }
            })
        }

//        runBlocking {  daoViewModel.deleteAll()}
//        runBlocking {  daoViewModel.deleteAllUserDB()}
//        runBlocking {  daoViewModel.deleteAllCrossDB()}

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