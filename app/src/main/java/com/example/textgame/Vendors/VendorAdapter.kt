package com.example.textgame.Vendors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.textgame.GameActivity
import com.example.textgame.R
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.classes.REFRESH
import com.example.textgame.classes.scopeDef
import com.example.textgame.hero.Hero
import kotlinx.coroutines.*


class VendorAdapter(
    val id: MutableList<Any?>,
    val name: MutableList<Any?>,
    val img: MutableList<Any?>,
    val pts: MutableList<Any?>,
    val price: MutableList<Any?>,
    val type: Int,
    var db: Maindb,
    var hero: Hero,
    private val activity: FragmentActivity,
    ) : RecyclerView.Adapter<VendorAdapter.My2ViewHolder>() {

    class My2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.rec2Name)
        val txtImg: ImageView = itemView.findViewById(R.id.rec2Img)
        val txtPt: TextView = itemView.findViewById(R.id.rec2PTs)
        val txtPrice: TextView = itemView.findViewById(R.id.rec2Price)
        val btnTrade: ImageView = itemView.findViewById(R.id.rec2BtnDeal)
        val txtInput: TextView = itemView.findViewById(R.id.rec2inptTxtPTs)
        val txtInfPt: TextView = itemView.findViewById(R.id.rec2infPTs)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): My2ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.vendor_recycle_adapter, parent, false)

        return My2ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: My2ViewHolder, position: Int) {
        holder.txtName.text = name[position].toString()
        holder.txtImg.setImageResource(img[position] as Int)
        holder.txtPt.text = pts[position].toString()
        holder.txtPrice.text = price[position].toString()
        holder.txtInput.text
        if (type == 2) {
            holder.txtPt.visibility = View.VISIBLE
            holder.txtInfPt.visibility = View.VISIBLE
        }
        if (type == 3) {holder.txtInput.visibility = View.GONE}

        holder.btnTrade.setOnClickListener {
            scopeDef.launch {
                REFRESH(db, (activity as GameActivity).bind, hero)
                delay(30)

                if (type == 1) {
                    ForVendors().vendorSell(db, id[position].toString().toInt(), hero, holder, activity) // покупка у вендра
                }
                if (type == 2) {
                    ForVendors().vendorBuy(db, position, hero, holder, activity,this@VendorAdapter) // продажа вендору
                }
                if (type == 3) {
                    ForVendors().vendorReBuy(db, hero, activity,this@VendorAdapter,position)                              // выкуп проданного
                }
            }
        }
    }

    override fun getItemCount() = name.size
}





