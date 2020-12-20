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

/**Logging into the application.*/
class LoginFragment : Fragment() {
    private lateinit var daoViewModel: DaoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

        val name = view.findViewById<TextView>(R.id.editTextTextPersonName)
        val pass = view.findViewById<TextView>(R.id.editTextTextPassword)
        pass.transformationMethod = PasswordTransformationMethod()

        /**The code below search for the given user in database.*/
        runBlocking {
            daoViewModel.readAllCross.observe(viewLifecycleOwner, {cross->
                runBlocking {
                    daoViewModel.readAllUser.observe(viewLifecycleOwner,{usr->
                        runBlocking {
                            daoViewModel.readAllData.observe(viewLifecycleOwner, {rest->

                                val login = view.findViewById<Button>(R.id.loginButton)
                                login.setOnClickListener {
                                    /**If the login fields where filled in, then check if the given user is a valid user or not.*/
                                    if (name.text.toString().isNotEmpty() && pass.text.toString().isNotEmpty()) {

                                        runBlocking {
                                            /**If doesn't exist in the database, the login fail, else
                                             * the given user will become the current user(Constants.user).*/
                                            daoViewModel.getUserDB(name.text.toString()).observe(viewLifecycleOwner, {usr->
                                                if (usr == null || usr.password != pass.text.toString()) {
                                                    Toast.makeText(context, "Wrong nickname or password!", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    Constants.user = usr

                                                    /**The current user's favorite restaurants will be stored into a list(Constants.userList)
                                                     * which will be passed as parameter to the favoriteFragment's recycleView's adapter.*/
                                                    for(v in cross){
                                                        if(v.userID == usr.userID){
                                                            for(w in rest){
                                                                if(v.id == w.id){
                                                                    Constants.userList.add(w)
                                                                }
                                                            }
                                                        }
                                                    }

                                                    Toast.makeText(context, "Welcome ${name.text}!", Toast.LENGTH_SHORT).show()

                                                    /**Moving to the profileFragment.*/
                                                    val transaction =
                                                        (context as FragmentActivity).supportFragmentManager.beginTransaction()
                                                    transaction.replace(R.id.nav_host_fragment, ProfileFragment())
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

        /**If the user choose the registration option, then moving to the
         * registerFragment.*/
        val register = view.findViewById<Button>(R.id.registerButton)
        register.setOnClickListener {
            /**Transferring the given username and password.*/
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