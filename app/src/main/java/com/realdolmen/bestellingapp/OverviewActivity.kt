package com.realdolmen.bestellingapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.firebase.database.*
import java.util.ArrayList

class OverviewActivity : AppCompatActivity() {


    //initialize
    internal lateinit var editTextName: EditText
    internal lateinit var editTextEmail: EditText
    internal lateinit var editTextNumber: EditText

    internal lateinit var buttonAddUser: Button
    internal lateinit var listViewUsers: ListView


    //a list to store all the User from firebase database
    internal lateinit var Users: MutableList<User>

    internal lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        // method for find ids of views
        findViews()

        // to maintian click listner of views
        initListner()
    }


    private fun findViews() {
        //getRefrance for user table
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        editTextName = findViewById<View>(R.id.editTextName) as EditText
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        editTextNumber = findViewById<View>(R.id.editTextNumber) as EditText
        listViewUsers = findViewById<View>(R.id.listViewUsers) as ListView
        buttonAddUser = findViewById<View>(R.id.buttonAddUser) as Button
        //list for store objects of user
        Users = ArrayList()
    }

    private fun initListner() {
        //adding an onclicklistener to button
        buttonAddUser.setOnClickListener {
            //calling the method addUser()
            //the method is defined below
            //this method is actually performing the write operation
            addUser()
        }

        // list item click listener
        listViewUsers.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val User = Users[i]
            CallUpdateAndDeleteDialog(User.userid!!, User.username!!, User.useremail!!, User.usermobileno!!)
        }
    }

    override fun onStart() {
        super.onStart()

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //clearing the previous User list
                Users.clear()

                //getting all nodes
                for (postSnapshot in dataSnapshot.children) {
                    //getting User from firebase console
                    val user = postSnapshot.getValue(User::class.java)
                    //adding User to the list
                    if (user != null) Users.add(user)
                }
                //creating Userlist adapter
                val UserAdapter = UserList(this@OverviewActivity, Users)
                //attaching adapter to the listview
                listViewUsers.adapter = UserAdapter
            }


            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    private fun CallUpdateAndDeleteDialog(userid: String, username: String, email: String, monumber: String) {

        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(dialogView)
        //Access Dialog views
        val updateTextname = dialogView.findViewById<View>(R.id.updateTextname) as EditText
        val updateTextemail = dialogView.findViewById<View>(R.id.updateTextemail) as EditText
        val updateTextmobileno = dialogView.findViewById<View>(R.id.updateTextmobileno) as EditText
        updateTextname.setText(username)
        updateTextemail.setText(email)
        updateTextmobileno.setText(monumber)
        val buttonUpdate = dialogView.findViewById<View>(R.id.buttonUpdateUser) as Button
        val buttonDelete = dialogView.findViewById<View>(R.id.buttonDeleteUser) as Button
        //username for set dialog title
        dialogBuilder.setTitle(username)
        val b = dialogBuilder.create()
        b.show()

        // Click listener for Update data
        buttonUpdate.setOnClickListener {
            val name = updateTextname.text.toString().trim { it <= ' ' }
            val email = updateTextemail.text.toString().trim { it <= ' ' }
            val mobilenumber = updateTextmobileno.text.toString().trim { it <= ' ' }
            //checking if the value is provided or not Here, you can Add More Validation as you required

            if (!TextUtils.isEmpty(name)) {
                if (!TextUtils.isEmpty(email)) {
                    if (!TextUtils.isEmpty(mobilenumber)) {
                        //Method for update data
                        updateUser(userid, name, email, mobilenumber)
                        b.dismiss()
                    }
                }
            }
        }

        // Click listener for Delete data
        buttonDelete.setOnClickListener {
            //Method for delete data
            deleteUser(userid)
            b.dismiss()
        }
    }

    private fun updateUser(id: String, name: String, email: String, mobilenumber: String): Boolean {
        //getting the specified User reference
        val UpdateReference = FirebaseDatabase.getInstance().getReference("Users").child(id)
        val User = User(id, name, email, mobilenumber)
        //update  User  to firebase
        UpdateReference.setValue(User)
        Toast.makeText(applicationContext, "User Updated", Toast.LENGTH_LONG).show()
        return true
    }

    private fun deleteUser(id: String): Boolean {
        //getting the specified User reference
        val DeleteReference = FirebaseDatabase.getInstance().getReference("Users").child(id)
        //removing User
        DeleteReference.removeValue()
        Toast.makeText(applicationContext, "User Deleted", Toast.LENGTH_LONG).show()
        return true
    }


    private fun addUser() {


        //getting the values to save
        val name = editTextName.text.toString().trim { it <= ' ' }
        val email = editTextEmail.text.toString().trim { it <= ' ' }
        val mobilenumber = editTextNumber.text.toString().trim { it <= ' ' }


        //checking if the value is provided or not Here, you can Add More Validation as you required

        if (!TextUtils.isEmpty(name)) {
            if (!TextUtils.isEmpty(email)) {
                if (!TextUtils.isEmpty(mobilenumber)) {

                    //it will create a unique id and we will use it as the Primary Key for our User
                    val id = databaseReference.push().key
                    //creating an User Object
                    val User = User(id!!, name, email, mobilenumber)
                    //Saving the User
                    databaseReference.child(id!!).setValue(User)

                    editTextName.setText("")
                    editTextNumber.setText("")
                    editTextEmail.setText("")
                    Toast.makeText(this, "User added", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Please enter a mobilenumber", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please enter a Email", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    //sms doorsturen als aankoop werd gemaakt
    //checkbox voor of na aflevering

    /* fun sendSMS()
     {
         val uri = Uri.parse("smsto:12346556")
         val intent = Intent(Intent.ACTION_SENDTO, uri)
         intent.putExtra("sms_body", "Here goes your message...")
         startActivity(intent)
     }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {

                Toast.makeText(applicationContext, "click on setting", Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_exit ->{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext, "click on exit", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}