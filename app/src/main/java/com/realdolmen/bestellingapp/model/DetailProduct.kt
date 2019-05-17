package com.realdolmen.bestellingapp.model

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.accessibility.AccessibilityEventCompat.setAction
import com.realdolmen.bestellingapp.R
import kotlinx.android.synthetic.main.activity_detail_product.*
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.realdolmen.bestellingapp.MainActivity
import com.realdolmen.bestellingapp.ScannerActivity


class DetailProduct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        countryDetail.text = intent.getStringExtra("country")
        naamDetail.text = intent.getStringExtra("name")
      priceDetial.text = intent.getStringExtra("price")
       //extraDetial.text = intent.getStringExtra("info")
        imageDetail.setImageResource(intent.getIntExtra("image",DEFAULT_BUFFER_SIZE))

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val uri = Uri.parse("smsto:12346556")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", "Hello I have a question...")
            startActivity(intent)

        }



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
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                Toast.makeText(applicationContext, "click on exit", Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_scanner ->{
                val i = Intent(this, ScannerActivity::class.java)
                startActivity(i)
                Toast.makeText(applicationContext, "click on exit", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
