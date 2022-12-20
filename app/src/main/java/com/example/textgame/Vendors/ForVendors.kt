package com.example.textgame.Vendors

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.textgame.GameActivity
import com.example.textgame.RoomDao.LootDb
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.RoomDao.ReBuy
import com.example.textgame.VendorTradeFragment
import com.example.textgame.classes.*
import com.example.textgame.databinding.FragmentVendorTradeBinding
import com.example.textgame.hero.Hero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForVendors {
    val id = mutableListOf<Any?>()
    val name = mutableListOf<Any?>()
    val img = mutableListOf<Any?>()
    val pts = mutableListOf<Any?>()
    val price = mutableListOf<Any?>()

    var idTemp = 0
    var icoTemp = 0
    var nameTemp = ""
    var typeTemp = ""
    var atkTemp = 0
    var matkTemp = 0
    var critTemp = 0
    var dodgeTemp = 0
    var blockTemp = 0
    var defTemp = 0
    var mdefTemp = 0
    var ptsTemp = 0
    var priceTemp = 0

    fun loadFromDbToVendorSellRecycle(
        db: Maindb,
        hero: Hero,
        bind: FragmentVendorTradeBinding,
        activity: FragmentActivity,
        vendorTradeFragment: VendorTradeFragment
    ) {
        val recyclerView: RecyclerView = bind.recyclerVendor
        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        import("id", id,vendorTradeFragment)
        import("img", img,vendorTradeFragment)
        import("name", name,vendorTradeFragment)
        import("price", price,vendorTradeFragment)
        import("", pts,vendorTradeFragment)

        recyclerView.adapter = VendorAdapter(id, name, img, pts, price, 1, db, hero, activity)
    }


    suspend fun vendorSell(
        db: Maindb,
        position: Int,
        hero: Hero,
        holder: VendorAdapter.My2ViewHolder,
        activity: GameActivity,
    ) {
        db.getDao().getLoot().toList().forEach {
            if (position == it.id) {
                idTemp = it.id
                icoTemp = it.ico
                nameTemp = it.name
                typeTemp = it.type
                atkTemp = it.atk
                matkTemp = it.matk
                critTemp = it.crit
                dodgeTemp = it.dodge
                blockTemp = it.block
                defTemp = it.def
                mdefTemp = it.mdef
                ptsTemp = it.PTs
                priceTemp = it.price
            }
        }

        if (holder.txtInput.text.isNullOrBlank()) {
            ptsTemp += 1
            if (priceTemp > hero.coin) {
                withContext(Dispatchers.Main){ Toast.makeText(activity.applicationContext,"Недостаточно золота",Toast.LENGTH_SHORT).show()}
            } else {
                hero.coin -= priceTemp
                db.getDao().insertLoot(LootDb(idTemp,
                    icoTemp,
                    nameTemp,
                    typeTemp,
                    atkTemp,
                    matkTemp,
                    critTemp,
                    dodgeTemp,
                    blockTemp,
                    defTemp,
                    mdefTemp,
                    ptsTemp,
                    priceTemp))
            }
        } else {
            val forPts = holder.txtInput.text.toString().toInt()
            ptsTemp += forPts
            val priceall = holder.txtInput.text.toString().toInt() * priceTemp
            if (priceall >= hero.coin) {
                withContext(Dispatchers.Main){ Toast.makeText(activity.applicationContext,"Недостаточно золота",Toast.LENGTH_SHORT).show()}
            } else {
                hero.coin -= priceall
                db.getDao().insertLoot(LootDb(idTemp,
                    icoTemp,
                    nameTemp,
                    typeTemp,
                    atkTemp,
                    matkTemp,
                    critTemp,
                    dodgeTemp,
                    blockTemp,
                    defTemp,
                    mdefTemp,
                    ptsTemp,
                    priceTemp))
            }
        }
        SAVE_HERO(db, hero)
        delay(30)
        REFRESH(db, activity.bind, hero)
        withContext(Dispatchers.Main) {
            holder.btnTrade.animate().apply {
                duration = 400
                rotationY(360f)
            }.withEndAction { holder.btnTrade.animate().apply { rotationY(0f) }  }
        }

    }

    fun loadFromDbToVendorBuyRecycle(
        db: Maindb,
        hero: Hero,
        bind: FragmentVendorTradeBinding,
        activity: FragmentActivity,
    ) {
        val recyclerView: RecyclerView = bind.recyclerVendor
        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = VendorAdapter(id, name, img, pts, price, 2, db, hero, activity)

        scopeDef.launch {
            db.getDao().getLoot().toList().forEach() {
                withContext(Dispatchers.Main) {
                    if (it.PTs >= 1) {
                        id.add(it.id)
                        img.add(it.ico)
                        name.add(it.name)
                        pts.add(it.PTs)
                        price.add(it.price)
                        delay(30)
                        recyclerView.adapter =
                            VendorAdapter(id, name, img, pts, price, 2, db, hero, activity)
                    }
                }
            }
        }
    }


    suspend fun vendorBuy(
        db: Maindb, position: Int, hero: Hero,
        holder: VendorAdapter.My2ViewHolder, activity: GameActivity, vendorAdapter: VendorAdapter,
    ) {
        db.getDao().getLoot().toList().forEach {
            if (vendorAdapter.id[position] == it.id) {
                idTemp = it.id
                icoTemp = it.ico
                nameTemp = it.name
                typeTemp = it.type
                atkTemp = it.atk
                matkTemp = it.matk
                critTemp = it.crit
                dodgeTemp = it.dodge
                blockTemp = it.block
                defTemp = it.def
                mdefTemp = it.mdef
                ptsTemp = it.PTs
                priceTemp = it.price
            }
        }
        delay(30)
        if (holder.txtInput.text.isNullOrBlank()) {
            ptsTemp -= 1
            hero.coin += priceTemp
            db.getDao().insertLoot(LootDb(idTemp, icoTemp, nameTemp, typeTemp, atkTemp, matkTemp,
                critTemp, dodgeTemp, blockTemp, defTemp, mdefTemp, ptsTemp, priceTemp))
            db.getDao().insertReBuy(ReBuy(idTemp, icoTemp, nameTemp, typeTemp, atkTemp, matkTemp,
                critTemp, dodgeTemp, blockTemp, defTemp, mdefTemp, 1, priceTemp))
        } else {
            if (ptsTemp < holder.txtInput.text.toString().toInt()) {
                withContext(Dispatchers.Main){ Toast.makeText(activity.applicationContext,"Недостаточное количество",Toast.LENGTH_SHORT).show()}
            } else {
                ptsTemp -= holder.txtInput.text.toString().toInt()
                hero.coin += priceTemp * holder.txtInput.text.toString().toInt()
                db.getDao()
                    .insertLoot(LootDb(idTemp, icoTemp, nameTemp, typeTemp, atkTemp, matkTemp,
                        critTemp, dodgeTemp, blockTemp, defTemp, mdefTemp, ptsTemp, priceTemp))
                db.getDao()
                    .insertReBuy(ReBuy(idTemp, icoTemp, nameTemp, typeTemp, atkTemp, matkTemp,
                        critTemp, dodgeTemp, blockTemp, defTemp, mdefTemp, 1, priceTemp))
            }
        }
        SAVE_HERO(db, hero)
        delay(30)
        REFRESH(db, activity.bind, hero)
        if (ptsTemp == 0) {
            withContext(Dispatchers.Main) {
                vendorAdapter.name.removeAt(position)
                vendorAdapter.img.removeAt(position)
                vendorAdapter.id.removeAt(position)
                vendorAdapter.pts.removeAt(position)
                vendorAdapter.price.removeAt(position)

                vendorAdapter.notifyItemRemoved(position)
                vendorAdapter.notifyItemRangeChanged(position, vendorAdapter.itemCount)
            }
        }else{
            withContext(Dispatchers.Main) {
                holder.txtPt.text = ptsTemp.toString()
                holder.btnTrade.animate().apply {
                    duration = 400
                    rotationYBy(360f)
                }.withEndAction { holder.btnTrade.animate().apply { rotationY(0f) } }
            }
        }

    }

    fun loadFromDbToVendorReBuyRecycle(
        db: Maindb,
        hero: Hero,
        bind: FragmentVendorTradeBinding,
        activity: FragmentActivity,
    ) {
        val recyclerView: RecyclerView = bind.recyclerVendor
        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = VendorAdapter(id, name, img, pts, price, 3, db, hero, activity)

        scopeDef.launch {
            db.getDao().getReBuy().toList().forEach() {
                withContext(Dispatchers.Main) {
                    if (it.PTs >= 1) {
                        id.add(it.id)
                        img.add(it.ico)
                        name.add(it.name)
                        pts.add(it.PTs)
                        price.add(it.price)
                        delay(30)
                        recyclerView.adapter =
                            VendorAdapter(id, name, img, pts, price, 3, db, hero, activity)
                    }
                }
            }
        }
    }

    suspend fun vendorReBuy(
        db: Maindb, hero: Hero, activity: GameActivity,
        vendorAdapter: VendorAdapter, position: Int,
    ) {
        db.getDao().getLoot().toList().forEach {
            if (vendorAdapter.id[position] == it.id) {
                idTemp = it.id
                icoTemp = it.ico
                nameTemp = it.name
                typeTemp = it.type
                atkTemp = it.atk
                matkTemp = it.matk
                critTemp = it.crit
                dodgeTemp = it.dodge
                blockTemp = it.block
                defTemp = it.def
                mdefTemp = it.mdef
                ptsTemp = it.PTs
                priceTemp = it.price
            }
        }
        delay(50)
        if (hero.coin >= priceTemp) {
            hero.coin -= priceTemp
            db.getDao().insertReBuy(ReBuy(idTemp, icoTemp, nameTemp, typeTemp, atkTemp, matkTemp,
                critTemp, dodgeTemp, blockTemp, defTemp, mdefTemp, 0, priceTemp))
            ptsTemp += 1
            db.getDao().insertLoot(LootDb(idTemp, icoTemp, nameTemp, typeTemp, atkTemp, matkTemp,
                critTemp, dodgeTemp, blockTemp, defTemp, mdefTemp, ptsTemp, priceTemp))
            SAVE_HERO(db, hero)
            delay(30)
            REFRESH(db, activity.bind, hero)
            withContext(Dispatchers.Main) {
                vendorAdapter.name.removeAt(position)
                vendorAdapter.img.removeAt(position)
                vendorAdapter.id.removeAt(position)
                vendorAdapter.pts.removeAt(position)
                vendorAdapter.price.removeAt(position)

                vendorAdapter.notifyItemRemoved(position)
                vendorAdapter.notifyItemRangeChanged(position, vendorAdapter.itemCount)
            }

        } else {
            withContext(Dispatchers.Main){ Toast.makeText(activity.applicationContext,"Недостаточно золота",Toast.LENGTH_SHORT).show()}
        }
    }


}

private fun import(idMap: String, list: MutableList<Any?>,vendorTradeFragment: VendorTradeFragment) {
    val vendor:List<Map<String, Any>>
    when (vendorTradeFragment.vendorNum){
        1-> vendor = Vendors().vendor1
        else -> vendor = Vendors().vendor0
    }
    var i = 0
    while (vendor.size > i) {
        list.add(vendor[i][idMap])
        i += 1
    }
}


