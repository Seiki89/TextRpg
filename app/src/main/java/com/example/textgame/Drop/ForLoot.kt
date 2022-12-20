package com.example.textgame.Drop

import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.textgame.GameActivity
import com.example.textgame.RoomDao.LootDb
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.classes.REFRESH
import com.example.textgame.classes.SAVE_HERO
import com.example.textgame.classes.scopeDef
import com.example.textgame.databinding.FragmentHeroBagBinding
import com.example.textgame.hero.Hero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForLoot() {
    var idL = mutableListOf<Int>()
    var imgL = mutableListOf<Int>()
    var namesL = mutableListOf<String>()
    var typesL = mutableListOf<String>()
    var ptsL = mutableListOf<Int>()
    var atkL = mutableListOf<Int>()
    var matkL = mutableListOf<Int>()
    var critL = mutableListOf<Int>()
    var dodgeL = mutableListOf<Int>()
    var blockL = mutableListOf<Int>()
    var defL = mutableListOf<Int>()
    val mdefL = mutableListOf<Int>()
    val priceL = mutableListOf<Int>()
    val hero = Hero()

    //шаблон вставки лута в базу. вызов : db,GoblinEar(),1
    fun insertLootDB(db: Maindb, dropList: DropList, pts: Int) {
        scopeDef.launch {
            db.getDao().insertLoot(LootDb(
                dropList.id,
                dropList.imgItem,
                dropList.nameItem,
                dropList.typeItem,
                dropList.atkItem,
                dropList.matkItem,
                dropList.critItem,
                dropList.dodgeItem,
                dropList.blockItem,
                dropList.defItem,
                dropList.mdefItem,
                pts,
                dropList.price)
            )
        }
    }

    //загрузка из базы данных в рецайклВью
    fun loadFromDBtoRecycle(db: Maindb, bind: FragmentHeroBagBinding, activity: FragmentActivity) {
        val recyclerView: RecyclerView = bind.recycleViewBag
        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = LootAdapter(idL, imgL, namesL, typesL, atkL, matkL,
            critL, dodgeL, blockL, defL, mdefL, ptsL, priceL, db, bind, hero, activity)

        scopeDef.launch {
            db.getDao().getLoot().toList().forEach() {
                withContext(Dispatchers.Main) {
                    if (it.PTs >= 1) {
                        idL.add(it.id)
                        imgL.add(it.ico)
                        namesL.add(it.name)
                        typesL.add(it.type)
                        ptsL.add(it.PTs)
                        atkL.add(it.atk)
                        matkL.add(it.matk)
                        critL.add(it.crit)
                        dodgeL.add(it.dodge)
                        blockL.add(it.block)
                        defL.add(it.def)
                        mdefL.add(it.mdef)
                        priceL.add(it.price)
                        delay(30)
                        recyclerView.adapter = LootAdapter(idL, imgL, namesL, typesL, atkL, matkL,
                            critL, dodgeL, blockL, defL, mdefL, ptsL, priceL, db, bind, hero, activity)
                    }
                }
            }
        }
    }

    fun removeFromRecycleToDb(
        db: Maindb,
        holder: LootAdapter.MyViewHolder,
        lootAdapter: LootAdapter,
        position: Int,
    ) {
        lootAdapter.ptsRC[position] -= 1
        scopeDef.launch {
            db.getDao().updLoot(LootDb(lootAdapter.idRC[position],
                lootAdapter.imgRC[position],
                lootAdapter.namesRC[position],
                lootAdapter.typesRC[position],
                lootAdapter.atkRC[position],
                lootAdapter.matkRC[position],
                lootAdapter.critRC[position],
                lootAdapter.dodgeRC[position],
                lootAdapter.blockRC[position],
                lootAdapter.defRC[position],
                lootAdapter.mdefRC[position],
                lootAdapter.ptsRC[position],
                lootAdapter.priceRC[position]))
            withContext(Dispatchers.Main) {
                delay(30)
                if (lootAdapter.ptsRC[position] > 0) {
                    holder.txtPT.text = lootAdapter.ptsRC[position].toString()
                    holder.btnDrop.animate().apply {
                        duration = 400
                        rotationYBy(360f)
                    }.withEndAction { holder.btnDrop.animate().apply { rotationY(0f) }  }
                } else {
                    lootAdapter.idRC.removeAt(position)
                    lootAdapter.imgRC.removeAt(position)
                    lootAdapter.namesRC.removeAt(position)
                    lootAdapter.typesRC.removeAt(position)
                    lootAdapter.atkRC.removeAt(position)
                    lootAdapter.matkRC.removeAt(position)
                    lootAdapter.critRC.removeAt(position)
                    lootAdapter.dodgeRC.removeAt(position)
                    lootAdapter.blockRC.removeAt(position)
                    lootAdapter.defRC.removeAt(position)
                    lootAdapter.mdefRC.removeAt(position)
                    lootAdapter.ptsRC.removeAt(position)
                    lootAdapter.priceRC.removeAt(position)

                    lootAdapter.notifyItemRemoved(position)
                    lootAdapter.notifyItemRangeChanged(position, lootAdapter.itemCount)
                }
            }
        }
    }

    fun updateEatButton(
        db: Maindb,
        activity: GameActivity,
        lootAdapter: LootAdapter,
        position: Int,
        holder: LootAdapter.MyViewHolder,
    ) {
        scopeDef.launch {
            val hero = Hero()
            hero.statsRefreshLoad(hero, db)
            delay(30)

            hero.hp += lootAdapter.atkRC[position]
            hero.sp += lootAdapter.matkRC[position]
            if (hero.hp > hero.maxhp) { hero.hp = hero.maxhp }
            if (hero.hp == 0) { hero.hp = 0 }
            if (hero.sp > hero.maxsp) { hero.sp = hero.maxsp }

            lootAdapter.ptsRC[position] -= 1
            db.getDao().updLoot(LootDb(lootAdapter.idRC[position],
                lootAdapter.imgRC[position],
                lootAdapter.namesRC[position],
                lootAdapter.typesRC[position],
                lootAdapter.atkRC[position],
                lootAdapter.matkRC[position],
                lootAdapter.critRC[position],
                lootAdapter.dodgeRC[position],
                lootAdapter.blockRC[position],
                lootAdapter.defRC[position],
                lootAdapter.mdefRC[position],
                lootAdapter.ptsRC[position],
                lootAdapter.priceRC[position]))
            withContext(Dispatchers.Main) {
                delay(30)
                if (lootAdapter.ptsRC[position] > 0) {
                    holder.txtPT.text = lootAdapter.ptsRC[position].toString()
                    holder.btnEat.animate().apply {
                        duration = 400
                        rotationYBy(360f)
                    }.withEndAction { holder.btnEat.animate().apply { rotationY(0f) }  }
                } else {
                    lootAdapter.idRC.removeAt(position)
                    lootAdapter.imgRC.removeAt(position)
                    lootAdapter.namesRC.removeAt(position)
                    lootAdapter.typesRC.removeAt(position)
                    lootAdapter.atkRC.removeAt(position)
                    lootAdapter.matkRC.removeAt(position)
                    lootAdapter.critRC.removeAt(position)
                    lootAdapter.dodgeRC.removeAt(position)
                    lootAdapter.blockRC.removeAt(position)
                    lootAdapter.defRC.removeAt(position)
                    lootAdapter.mdefRC.removeAt(position)
                    lootAdapter.ptsRC.removeAt(position)
                    lootAdapter.priceRC.removeAt(position)

                    lootAdapter.notifyItemRemoved(position)
                    lootAdapter.notifyItemRangeChanged(position, lootAdapter.itemCount)
                }
                SAVE_HERO(db, hero)
                delay(30)
                REFRESH(db, activity.bind, hero)
            }
        }
    }

}