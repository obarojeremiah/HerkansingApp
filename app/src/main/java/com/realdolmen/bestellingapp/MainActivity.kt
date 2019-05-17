package com.realdolmen.bestellingapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn.getClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.realdolmen.bestellingapp.model.Bestelling2
import com.realdolmen.bestellingapp.model.ListCardItems
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.annotations.Nullable

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 7


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = getClient(this, gso)
        var signIn: SignInButton = findViewById(R.id.sign_in_button)
        var signOut: Button = findViewById(R.id.sign_out_button)
        signIn.setOnClickListener(this)
        signOut.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.sign_in_button -> signIn()
            R.id.sign_out_button -> signOut()
            else -> println("Number too high")
        }
    }

    private fun signOut() {

        mGoogleSignInClient!!.signOut()
        //val i = Intent(this, Bestellen::class.java)
        //startActivity(i)
    }


    private fun signIn() {

        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val i = Intent(this, ProductCard::class.java)
            startActivity(i)
        } catch (e: ApiException) {
            Log.w("test", "signInResult:failed code=" + e.statusCode)
        }

    }

/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }





    fun Click(v: View) {
        val intent = Intent(this, Bestelling2::class.java)
        startActivity(intent)
    }
    fun Product(v: View) {
        val intent = Intent(this, ProductCard::class.java)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(applicationContext, "click on setting", Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_exit ->{
             Toast.makeText(applicationContext, "click on exit", Toast.LENGTH_LONG).show()
              true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
*/

    //sms doorsturen als aankoop werd gemaakt
    //checkbox voor of na aflevering

   /* fun sendSMS()
    {
        val uri = Uri.parse("smsto:12346556")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra("sms_body", "Here goes your message...")
        startActivity(intent)
    }*/

}

