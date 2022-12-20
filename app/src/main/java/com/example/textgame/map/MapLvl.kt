package com.example.textgame.map

import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.example.textgame.FightFragment
import com.example.textgame.GameActivity
import com.example.textgame.R
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.VendorTradeFragment
import com.example.textgame.classes.DataModel
import com.example.textgame.classes.SAVE_HERO
import com.example.textgame.classes.random
import com.example.textgame.databinding.FragmentQuestBinding
import com.example.textgame.databinding.FragmentVendorTradeBinding
import com.example.textgame.hero.Hero
import com.example.textgame.hero.UpBar
import com.example.textgame.monsters.Goblin
import com.example.textgame.monsters.Monster
import com.example.textgame.monsters.MonsterError


class MapLevels(
    val bind: FragmentQuestBinding,
    val hero: Hero,
    val db: Maindb,
    private val activity: FragmentActivity,
) {
    private val dataModel: DataModel by activity.viewModels()
    val rndMonster: Int
        get() = random(0, 1)

    //отображение и смена локации на карте
    fun changeLvl() {
        when (hero.mapLvl) {
            999 -> MapLevels(bind, hero, db, activity).mapTitle1()
            998 -> MapLevels(bind, hero, db, activity).mapTitle2()
            997 -> MapLevels(bind, hero, db, activity).mapTitle3()
            996 -> MapLevels(bind, hero, db, activity).mapTitle4()

            1 -> MapLevels(bind, hero, db, activity).mapLevel1()
            1001 -> MapLevels(bind, hero, db, activity).mapLevel1001()
            2 -> MapLevels(bind, hero, db, activity).mapLevel2()
            else -> {}
        }
    }

    //шаблон уровня
    private fun mapEngine(
        imgRes: Int,
        txtDescriptionRes: Int,
        mob: Monster,
        mobNum: Int,
        btnNum: Int,
    ) {
        var mobAfterNum = 0
        //разметитить интерфейс
        bind.imgLocation.setImageResource(imgRes)
        bind.txtLocationDescription.setText(txtDescriptionRes)
        bind.btnAtack.visibility = View.GONE
        bind.btnAct1.visibility = View.GONE
        bind.btnAct2.visibility = View.GONE
        bind.btnAct3.visibility = View.GONE
        bind.btnAct4.visibility = View.GONE
        bind.btnAct5.visibility = View.GONE

        //если на уровне есть монстр то устроить с ним бой и описать его
        dataModel.msgNumMonsterForFight.value = mobNum
        dataModel.msgNumMonsterAfterFight.observe(activity as LifecycleOwner) { mobAfterNum = it }
        if (mobNum >= 1 && mobAfterNum >= 1) {
            bind.txtLocationDescription.append("\n Рядом ${mob.name}, на первый взгляд: ${mob.description}")
            bind.btnAtack.visibility = View.VISIBLE
            bind.btnAtack.setOnClickListener {
                dataModel.msgMonsterForFight.value = mob
                activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.placeHolder, FightFragment.newInstance())
                    .commit()
            }
        }
        //если монстров уже нет то продолжить путь
        dataModel.msgNumMonsterAfterFight.observe(activity as LifecycleOwner) { mobAfterNum = it }
        if (btnNum == 1 && mobAfterNum == 0 || btnNum == 1 && mobNum == 0) {
            bind.btnAct1.visibility = View.VISIBLE
        }
        if (btnNum == 2 && mobAfterNum == 0 || btnNum == 2 && mobNum == 0) {
            bind.btnAct1.visibility = View.VISIBLE
            bind.btnAct2.visibility = View.VISIBLE
        }
        if (btnNum == 3 && mobAfterNum == 0 || btnNum == 3 && mobNum == 0) {
            bind.btnAct1.visibility = View.VISIBLE
            bind.btnAct2.visibility = View.VISIBLE
            bind.btnAct3.visibility = View.VISIBLE
        }
        if (btnNum == 4 && mobAfterNum == 0 || btnNum == 4 && mobNum == 0) {
            bind.btnAct1.visibility = View.VISIBLE
            bind.btnAct2.visibility = View.VISIBLE
            bind.btnAct3.visibility = View.VISIBLE
            bind.btnAct4.visibility = View.VISIBLE
        }
        if (btnNum == 5 && mobAfterNum == 0 || btnNum == 5 && mobNum == 0) {
            bind.btnAct1.visibility = View.VISIBLE
            bind.btnAct2.visibility = View.VISIBLE
            bind.btnAct3.visibility = View.VISIBLE
            bind.btnAct4.visibility = View.VISIBLE
            bind.btnAct5.visibility = View.VISIBLE
        }
        dataModel.msgNumMonsterAfterFight.value = 1
    }
//Вступление
    private fun mapTitle1() {
        mapEngine(R.drawable.title_01, R.string.title_01_1, MonsterError(), 0, 2)

        bind.btnAct1.setText(R.string.title_btn_blind)
        bind.btnAct1.setOnClickListener {
            val move = random(1, 10)
            if (move == 4) {
                hero.mapLvl = 1
                SAVE_HERO(db, hero)
                changeLvl()
            } else {
                hero.hp -= random(1,5)
                UpBar().statCompl(db, (activity as GameActivity).bind, hero)
                SAVE_HERO(db, hero)
                bind.txtLocationDescription.append (activity.resources.getText(R.string.title_head_bash))
            }
        }

        bind.btnAct2.setText(R.string.title_find)
        bind.btnAct2.setOnClickListener {
            hero.mapLvl = 998
            SAVE_HERO(db, hero)
            changeLvl()
        }
    }

    private fun mapTitle2() {
        mapEngine(R.drawable.title_01, R.string.title_01_2, MonsterError(), 0, 2)

        bind.btnAct1.setText(R.string.title_btn_blind)
        bind.btnAct1.setOnClickListener {
            val move = random(1, 5)
            if (move == 4) {
                hero.mapLvl = 1
                SAVE_HERO(db, hero)
                changeLvl()
            } else {
                hero.hp -= random(3,7)
                UpBar().statCompl(db, (activity as GameActivity).bind, hero)
                SAVE_HERO(db, hero)
                bind.txtLocationDescription.append (activity.resources.getText(R.string.title_head_bash))
            }
        }

        bind.btnAct2.setText(R.string.title_fire)
        bind.btnAct2.setOnClickListener {
            val fire = random(1,4)
            if (fire == 2) {
                hero.mapLvl = 997
                SAVE_HERO(db, hero)
                changeLvl()
            }else{
                bind.txtLocationDescription.append(activity.resources.getText(R.string.title_fire_broke))
            }
        }
    }

    private fun mapTitle3() {
        mapEngine(R.drawable.title_02, R.string.title_03, MonsterError(), 0, 1)

        bind.btnAct1.setText(R.string.title_Wait)
        bind.btnAct1.setOnClickListener {
                hero.mapLvl = 996
                SAVE_HERO(db, hero)
                changeLvl()
        }
    }

    private fun mapTitle4() {
        mapEngine(R.drawable.title_03, R.string.title_04, MonsterError(), 0, 1)

        bind.btnAct1.setText(R.string.title_exit)
        bind.btnAct1.setOnClickListener {
            hero.mapLvl = 1
            SAVE_HERO(db, hero)
            changeLvl()
        }
    }


    private fun mapLevel1() {
        mapEngine(R.drawable.map_loc01, R.string.loc_01_desc, Goblin(), 0, 2)

        bind.btnAct1.setText(R.string.GoToBridge)
        bind.btnAct1.setOnClickListener {
            hero.mapLvl = 2
            SAVE_HERO(db, hero)
            changeLvl()
        }
        bind.btnAct2.text = "Осмотреться на местности"
        bind.btnAct2.setOnClickListener {
            hero.mapLvl = 1001
            SAVE_HERO(db, hero)
            changeLvl()
        }
    }

    private fun mapLevel1001() {
        mapEngine(R.drawable.map_loc1001, R.string.loc_01_desc, Goblin(), 0, 5)
        bind.txtLocationDescription.text = "из за башни выходит девушка"

        bind.btnAct1.setText(R.string.GoToBridge)
        bind.btnAct1.setOnClickListener {
            hero.mapLvl = 2
            SAVE_HERO(db, hero)
            changeLvl()
        }
        bind.btnAct2.text = "Купить"
        bind.btnAct2.setOnClickListener {
            dataModel.msgTradeType.value = 1
            dataModel.msgVendorNum.value = 1
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.placeHolder, VendorTradeFragment.newInstance())
                .commit()
        }
        bind.btnAct3.text = "Продать"
        bind.btnAct3.setOnClickListener {
            dataModel.msgTradeType.value = 2
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.placeHolder, VendorTradeFragment.newInstance())
                .commit()
        }
        bind.btnAct4.text = "Выкуп"
        bind.btnAct4.setOnClickListener {
            dataModel.msgTradeType.value = 3
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.placeHolder, VendorTradeFragment.newInstance())
                .commit()

        }
        bind.btnAct5.text = "диалог2"
        bind.btnAct5.setOnClickListener {
        }


    }


    private fun mapLevel2() {
        mapEngine(R.drawable.map_loc02, R.string.loc_02_desc, Goblin(), rndMonster, 1)
        bind.btnAct1.setText(R.string.BackWatchtower)
        bind.btnAct1.setOnClickListener {
            hero.mapLvl = 1
            SAVE_HERO(db, hero)
            changeLvl()
        }
    }


}
