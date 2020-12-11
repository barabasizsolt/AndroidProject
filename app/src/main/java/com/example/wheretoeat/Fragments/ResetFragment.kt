package com.example.wheretoeat.Fragments

import android.os.Bundle
import android.provider.SyncStateContract
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


class ResetFragment : Fragment() {
    private lateinit var daoViewModel: DaoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_reset, container, false)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

        val newPwd = view.findViewById<TextView>(R.id.resetTextView)

        val resetBtn = view.findViewById<Button>(R.id.resetButton)
        resetBtn.setOnClickListener {
            if(newPwd.text.toString().isNotEmpty()){
                daoViewModel.updatePasswordDB(newPwd.text.toString(), Constants.user.userID)
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