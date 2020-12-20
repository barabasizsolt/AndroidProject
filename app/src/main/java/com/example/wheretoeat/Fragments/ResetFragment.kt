package com.example.wheretoeat.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.wheretoeat.R
import com.example.wheretoeat.Util.Constants
import com.example.wheretoeat.ViewModel.DaoViewModel

/**Reset the current user's password.*/
class ResetFragment : Fragment() {
    private lateinit var daoViewModel: DaoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_reset, container, false)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

        /**TextView, where the user can set the new password.*/
        val newPassword = view.findViewById<TextView>(R.id.resetTextView)

        /**If the new password is not empty, reset and going back to the profileFragment.*/
        val resetButton = view.findViewById<Button>(R.id.resetButton)
        resetButton.setOnClickListener {
            if(newPassword.text.toString().isNotEmpty()){
                daoViewModel.updatePasswordDB(newPassword.text.toString(), Constants.user.userID)
                Toast.makeText(context, "Password updated!", Toast.LENGTH_SHORT).show()

                val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment, ProfileFragment())
                transaction.commit()
            }else{
                Toast.makeText(context, "Please enter the new password!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}