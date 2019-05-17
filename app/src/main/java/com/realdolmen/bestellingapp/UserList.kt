package com.realdolmen.bestellingapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class UserList(private val context: Activity, //list of users
               internal var Users: List<User>) : ArrayAdapter<User>(context, R.layout.layout_user_list, Users) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.layout_user_list, null, true)
        //initialize
        val textViewName = listViewItem.findViewById<View>(R.id.textViewName) as TextView
        val textviewemail = listViewItem.findViewById<View>(R.id.textviewemail) as TextView
        val textviewnumber = listViewItem.findViewById<View>(R.id.textviewnumber) as TextView

        //getting user at position
        val User = Users[position]
        //set user name
        textViewName.text = User.username
        //set user email
        textviewemail.text = User.useremail
        //set user mobilenumber
        textviewnumber.text = User.usermobileno

        return listViewItem
    }
}