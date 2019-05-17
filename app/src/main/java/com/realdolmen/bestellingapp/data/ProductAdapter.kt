package com.realdolmen.bestellingapp.data

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.realdolmen.bestellingapp.R
import com.realdolmen.bestellingapp.model.DetailProduct
import com.realdolmen.bestellingapp.model.ListCardItems

class ProductAdapter (private val list: ArrayList<ListCardItems>, private val context: Context) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(waffels: ListCardItems) {
            var waffelName: TextView = itemView.findViewById(R.id.lv_dishe) as TextView
            var countryOfOrigin: TextView = itemView.findViewById(R.id.lv_countryOfOrigin) as TextView
            var prijsWaffels: TextView = itemView.findViewById(R.id.lv_price) as TextView
            var imageofWaffel: ImageView = itemView.findViewById(R.id.imageView) as ImageView
            waffelName.text = waffels.type
            countryOfOrigin.text = waffels.origin
            prijsWaffels.text = waffels.price


            imageofWaffel.setImageResource(waffels.img)
            itemView.setOnClickListener{
                val intent = Intent(context, DetailProduct::class.java)
                intent.putExtra("name",waffels.type.toString())
                intent.putExtra("country",waffels.origin.toString())
                intent.putExtra("price",waffels.price.toString())
                intent.putExtra("info",waffels.extraInfoWaffles.toString())
                intent.putExtra("image", waffels.img!!)
                context.startActivity(intent)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ProductAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }



    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

}