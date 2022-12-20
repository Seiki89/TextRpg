package com.example.textgame.Drop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.textgame.GameActivity
import com.example.textgame.R
import com.example.textgame.RoomDao.HeroDb
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.classes.*
import com.example.textgame.databinding.FragmentHeroBagBinding
import com.example.textgame.hero.Hero
import com.example.textgame.hero.HeroBag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LootAdapter(
    var idRC: MutableList<Int>,
    var imgRC: MutableList<Int>,
    var namesRC: MutableList<String>,
    var typesRC: MutableList<String>,
    var atkRC: MutableList<Int>,
    var matkRC: MutableList<Int>,
    var critRC: MutableList<Int>,
    var dodgeRC: MutableList<Int>,
    var blockRC: MutableList<Int>,
    var defRC: MutableList<Int>,
    var mdefRC: MutableList<Int>,
    var ptsRC: MutableList<Int>,
    var priceRC: MutableList<Int>,
    var db: Maindb,
    var bind: FragmentHeroBagBinding,
    val hero: Hero,
    private val activity: FragmentActivity
) : RecyclerView.Adapter<LootAdapter.MyViewHolder>() {

    var posID = 0
    private val dataModel: DataModel by activity.viewModels()


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgLoot: ImageView = itemView.findViewById(R.id.recImgItem)
        val txtName: TextView = itemView.findViewById(R.id.recTxtItemName)
        val txtType: TextView = itemView.findViewById(R.id.recTxtItemType)
        val txtPT: TextView = itemView.findViewById(R.id.recTxtPT)
        val txtId: TextView = itemView.findViewById(R.id.recID)
        val btnDrop: ImageView = itemView.findViewById(R.id.recBtnDrop)
        val btnUse: ImageView = itemView.findViewById(R.id.recBtnUse)
        val btnInfoEq: ImageView = itemView.findViewById(R.id.recBtnEquipInfo)
        val btnEat: ImageView = itemView.findViewById(R.id.recBtnEat)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.bag_recycle_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        //обновление статов при вызове
        fun statsRefresh(bind: FragmentHeroBagBinding) {
            bind.txtATK.text = hero.atk.toString()
            bind.txtMATK.text = hero.matk.toString()
            bind.txtCRIT.text = hero.crit.toString()
            bind.txtDODGE.text = hero.dodge.toString()
            bind.txtBLOCK.text = hero.block.toString()
            bind.txtDEF.text = hero.def.toString()
            bind.txtMDEF.text = hero.mdef.toString()
        }

        //стартовая загрузка рецайкла
        holder.imgLoot.setImageResource(imgRC[position])
        holder.txtName.text = namesRC[position]
        holder.txtType.text = typesRC[position]
        holder.txtPT.text = ptsRC[position].toString()
        holder.txtId.text = idRC[position].toString()
        if (holder.txtType.text == "loot") {
            holder.btnDrop.visibility = View.VISIBLE
        }
        if (holder.txtType.text != "loot" && holder.txtType.text != "bottle") {
            holder.btnUse.visibility = View.VISIBLE
            holder.btnInfoEq.visibility = View.VISIBLE
        }
        if (holder.txtType.text == "bottle") {
            holder.btnEat.visibility = View.VISIBLE
            holder.btnInfoEq.visibility = View.VISIBLE
        }


        //кнопка информации
        holder.btnInfoEq.setOnClickListener {
            dataModel.msgEquipIdForInformation.value =
                listOf<Int>(atkRC[position],
                    matkRC[position],
                    critRC[position],
                    dodgeRC[position],
                    blockRC[position],
                    defRC[position],
                    mdefRC[position],
                    imgRC[position])
            dataModel.msgEquipIdForInformationName.value =
                listOf<String>(namesRC[position], typesRC[position])
            val myDialogFragment = MyDialogFragment()
            val manager = activity.supportFragmentManager
            myDialogFragment.show(manager, "PopUpDrop")
        }

        //кнопка одеть
        holder.btnUse.setOnClickListener {
            if (typesRC[position] == "two hand") {
                val itsId = idRC[position]
                scopeDef.launch {
                    hero.statsRefreshLoad(hero, db)
                    delay(50)
                    db.getDao().insertHero(HeroDb(1, hero.ava, hero.sex, hero.name, hero.prof,
                        hero.hp, hero.sp, hero.xp, hero.aura, hero.coin, hero.lvl, hero.mapLvl,
                        itsId, itsId, hero.chest, hero.leg))
                    withContext(Dispatchers.Main) {
                        HeroBag().applyEquipPic(db, bind)
                        HeroBag().applyEquipStat(db, hero)
                        delay(50)
                        statsRefresh(bind)
                    }
                }
            }
            if (typesRC[position] == "one handl") {
                val itsId = idRC[position]
                scopeDef.launch {
                    hero.statsRefreshLoad(hero, db)
                    delay(50)
                    if (hero.weapon == hero.shield) {
                        hero.shield = 0
                        bind.imgWeaponShield.setImageResource(R.drawable.empty_hero_shit)
                    }
                    db.getDao().insertHero(HeroDb(1, hero.ava, hero.sex, hero.name, hero.prof,
                        hero.hp, hero.sp, hero.xp, hero.aura, hero.coin, hero.lvl, hero.mapLvl,
                        itsId, hero.shield, hero.chest, hero.leg))
                    withContext(Dispatchers.Main) {
                        HeroBag().applyEquipPic(db, bind)
                        HeroBag().applyEquipStat(db, hero)
                        delay(50)
                        statsRefresh(bind)
                    }
                }
            }
            if (typesRC[position] == "one handr") {
                val itsId = idRC[position]
                scopeDef.launch {
                    hero.statsRefreshLoad(hero, db)
                    delay(50)
                    if (hero.weapon == hero.shield) {
                        hero.weapon = 0
                        bind.imgWeapon.setImageResource(R.drawable.empty_hero_weapon)
                    }
                    db.getDao().insertHero(HeroDb(1, hero.ava, hero.sex, hero.name, hero.prof,
                        hero.hp, hero.sp, hero.xp, hero.aura, hero.coin, hero.lvl, hero.mapLvl,
                        hero.weapon, itsId, hero.chest, hero.leg))
                    withContext(Dispatchers.Main) {
                        HeroBag().applyEquipPic(db, bind)
                        HeroBag().applyEquipStat(db, hero)
                        delay(50)
                        statsRefresh(bind)
                    }
                }
            }
            if (typesRC[position] == "chest") {
                bind.imgChest.setImageResource(imgRC[position])
                val itsId = idRC[position]
                scopeDef.launch {
                    hero.statsRefreshLoad(hero, db)
                    delay(50)
                    db.getDao().insertHero(HeroDb(1, hero.ava, hero.sex, hero.name, hero.prof,
                        hero.hp, hero.sp, hero.xp, hero.aura, hero.coin, hero.lvl, hero.mapLvl,
                        hero.weapon, hero.shield, itsId, hero.leg))
                    withContext(Dispatchers.Main) {
                        HeroBag().applyEquipPic(db, bind)
                        HeroBag().applyEquipStat(db, hero)
                        delay(50)
                        statsRefresh(bind)
                    }
                }
            }
            if (typesRC[position] == "leg") {
                bind.imgLeg.setImageResource(imgRC[position])
                val itsId = idRC[position]
                scopeDef.launch {
                    hero.statsRefreshLoad(hero, db)
                    delay(50)
                    db.getDao().insertHero(HeroDb(1, hero.ava, hero.sex, hero.name, hero.prof,
                        hero.hp, hero.sp, hero.xp, hero.aura, hero.coin, hero.lvl, hero.mapLvl,
                        hero.weapon, hero.shield, hero.chest, itsId))
                    withContext(Dispatchers.Main) {
                        HeroBag().applyEquipPic(db, bind)
                        HeroBag().applyEquipStat(db, hero)
                        delay(50)
                        statsRefresh(bind)
                    }
                }
            }
        }

        //кнопка выкинуть
        holder.btnDrop.setOnClickListener {
            ForLoot().removeFromRecycleToDb(db,holder,this@LootAdapter,position)
        }
        //кнопка сьесть
        holder.btnEat.setOnClickListener {
            ForLoot().updateEatButton(db,activity as GameActivity,this@LootAdapter,position,holder)


        }
    }


    override fun getItemCount() = namesRC.size

}





