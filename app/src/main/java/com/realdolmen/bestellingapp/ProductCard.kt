package com.realdolmen.bestellingapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.realdolmen.bestellingapp.data.ProductAdapter
import com.realdolmen.bestellingapp.model.ListCardItems
import kotlinx.android.synthetic.main.activity_product_card.*

class ProductCard : AppCompatActivity() {


    private var adapter: ProductAdapter? = null
    private var mealListCrds: ArrayList<ListCardItems>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_card)
        mealListCrds = ArrayList<ListCardItems>()
        layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(mealListCrds!!, this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        var imageIdList = arrayOf<Int>(
            R.drawable.chocolate,
            R.drawable.carre,
            R.drawable.thumb_wafels


        )
        var typeWaffelsList: Array<String> = arrayOf(
            "Chocolate",
            "square jam",
            "waffels"

        )
        var prijsWaffels: Array<String> = arrayOf(
            "2,30", "2,50","2,00"
        )
        var infoWaffels: Array<String> = arrayOf(
            "Chocolate is a usually sweet, brown food preparation of roasted and ground cacao seeds that is made in the form of a liquid, paste, or in a block, or used as a flavoring ingredient in other foods.",
            "Jam typically contains both the juice and flesh of a fruit ",
            "A classic waffle recipe includes basic ingredients you probably already have on hand, creating a perfectly crisp breakfast item."


        )
        var CountryWaffelsList: Array<String> = arrayOf(
            "Belgium",
            "Holland",
            "France"
        )

        for (i in 0..2) {
            var waffel = ListCardItems()
            waffel.type = typeWaffelsList[i]
            waffel.price = prijsWaffels[i]
            waffel.img = imageIdList[i]
            waffel.origin=infoWaffels[i]
           // waffel.originWaffles = CountryWaffelsList[i]

            mealListCrds?.add(waffel)
        }
        adapter!!.notifyDataSetChanged()

        recyclerView.setOnClickListener { }
    }

    fun toOrderPage(view : View){
        val intent = Intent(this, OverviewActivity::class.java)
        intent.action = Intent.ACTION_SEND
        //intent.putExtra(Intent.EXTRA_TEXT, "Welcome to the second activity" );
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
