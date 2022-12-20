package com.example.textgame

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.textgame.Drop.*
import com.example.textgame.RoomDao.LootDb
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.classes.*
import com.example.textgame.databinding.ActivityGameBinding
import com.example.textgame.databinding.FragmentFightBinding
import com.example.textgame.hero.Hero
import com.example.textgame.hero.UpBar
import com.example.textgame.monsters.Fight
import com.example.textgame.monsters.Monster
import com.example.textgame.monsters.MonsterError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FightFragment() : Fragment() {
    lateinit var bind: FragmentFightBinding
    private val dataModel: DataModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        bind = FragmentFightBinding.inflate(inflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hero = Hero()
        val db = Maindb.heroSetDb(requireActivity())
        bind.logBattle.movementMethod = ScrollingMovementMethod.getInstance()

        fun mageAtk() {
            if (hero.prof == "Mage" || hero.prof == "Маг") {
                if (hero.lvl in 1..2) {
                    bind.mageFire.visibility = View.VISIBLE
                }
                if (hero.lvl in 3..4) {
                    bind.mageFire.visibility = View.VISIBLE
                    bind.mageWatter.visibility = View.VISIBLE
                }
                if (hero.lvl in 5..6) {
                    bind.mageFire.visibility = View.VISIBLE
                    bind.mageWatter.visibility = View.VISIBLE
                    bind.mageEarth.visibility = View.VISIBLE
                    bind.mageWind.visibility = View.VISIBLE
                }
                if (hero.lvl in 8..10) {
                    bind.mageFire.visibility = View.VISIBLE
                    bind.mageWatter.visibility = View.VISIBLE
                    bind.mageEarth.visibility = View.VISIBLE
                    bind.mageWind.visibility = View.VISIBLE
                    bind.mageArkane.visibility = View.VISIBLE
                }
            }
        }

//прогрузка героя:
        REFRESH(db, (activity as GameActivity).bind, hero)

        scopeMain.launch {
            while (hero.name.isBlank()) {
                delay(20)
            }
            bind.txtNameHero.text = hero.name
            bind.imgAvaHero.setImageResource(Hero().avaSingle(hero.ava))
            mageAtk()
        }
// использование баночек
        var bigHP = 0
        var bigSP = 0
        var litHP = 0
        var litSP = 0
        fun bottleControl() {
            scopeDef.launch {
                var pt1 = 0
                var pt2 = 0
                var pt3 = 0
                var pt4 = 0
                db.getDao().getLoot().toList().forEach {
                    if (it.id == 999 && it.PTs >= 1) {
                        pt1 = it.PTs
                    }
                    if (it.id == 998 && it.PTs >= 1) {
                        pt2 = it.PTs
                    }
                    if (it.id == 997 && it.PTs >= 1) {
                        pt3 = it.PTs
                    }
                    if (it.id == 996 && it.PTs >= 1) {
                        pt4 = it.PTs
                    }
                }
                withContext(Dispatchers.Main) {
                    if (pt1 >= 1) {
                        bigHP = pt1
                        bind.btnHpMax.visibility = View.VISIBLE
                        bind.txtBigHpPt.visibility = View.VISIBLE
                        bind.txtBigHpPt.text = pt1.toString()
                    } else {
                        bind.btnHpMax.visibility = View.GONE
                        bind.txtBigHpPt.visibility = View.GONE
                    }
                    if (pt2 >= 1) {
                        bigSP = pt2
                        bind.btnSpMax.visibility = View.VISIBLE
                        bind.txtBigSpPt.visibility = View.VISIBLE
                        bind.txtBigSpPt.text = pt2.toString()
                    } else {
                        bind.btnSpMax.visibility = View.GONE
                        bind.txtBigSpPt.visibility = View.GONE
                    }
                    if (pt3 >= 1) {
                        litHP = pt3
                        bind.btnHpMin.visibility = View.VISIBLE
                        bind.txtMinHpPt.visibility = View.VISIBLE
                        bind.txtMinHpPt.text = pt3.toString()
                    } else {
                        bind.btnHpMin.visibility = View.GONE
                        bind.txtMinHpPt.visibility = View.GONE
                    }
                    if (pt4 >= 1) {
                        litSP = pt4
                        bind.btnSpMin.visibility = View.VISIBLE
                        bind.txtMinSpPt.visibility = View.VISIBLE
                        bind.txtMinSpPt.text = pt4.toString()
                    } else {
                        bind.btnSpMin.visibility = View.GONE
                        bind.txtMinSpPt.visibility = View.GONE
                    }
                }
            }
        }
        bottleControl()
        bind.btnHpMax.setOnClickListener {
            hero.hp += 70
            bigHP -= 1
            scopeDef.launch {
                ForLoot().insertLootDB(db, BottleHpBig(), bigHP)
                delay(50)
                bottleCheck(db, (activity as GameActivity).bind, hero)
                SAVE_HERO(db, hero)
                delay(50)
                withContext(Dispatchers.Main) {
                    bind.btnHpMax.animate().apply {
                        duration = 200
                        scaleXBy(5f)
                        scaleYBy(5f)
                    }.withEndAction {
                        bind.btnHpMax.animate().apply {
                            scaleXBy(-5f)
                            scaleYBy(-5f)
                        }
                    }
                    delay(210)
                    bottleControl()
                    REFRESH(db, (activity as GameActivity).bind, hero)
                }
            }
        }
        bind.btnSpMax.setOnClickListener {
            hero.sp += 70
            bigSP -= 1
            scopeDef.launch {
                ForLoot().insertLootDB(db, BottleMpBig(), bigSP)
                delay(50)
                bottleCheck(db, (activity as GameActivity).bind, hero)
                SAVE_HERO(db, hero)
                delay(50)
                withContext(Dispatchers.Main) {
                    bind.btnSpMax.animate().apply {
                        duration = 200
                        scaleXBy(5f)
                        scaleYBy(5f)
                    }.withEndAction {
                        bind.btnSpMax.animate().apply {
                            scaleXBy(-5f)
                            scaleYBy(-5f)
                        }
                    }
                    delay(210)
                    bottleControl()
                    REFRESH(db, (activity as GameActivity).bind, hero)
                }
            }
        }
        bind.btnHpMin.setOnClickListener {
            hero.hp += 40
            litHP -= 1
            scopeDef.launch {
                ForLoot().insertLootDB(db, BottleHpMin(), litHP)
                delay(50)
                bottleCheck(db, (activity as GameActivity).bind, hero)
                SAVE_HERO(db, hero)
                delay(50)
                withContext(Dispatchers.Main) {
                    bind.btnHpMin.animate().apply {
                        duration = 200
                        scaleXBy(5f)
                        scaleYBy(5f)
                    }.withEndAction {
                        bind.btnHpMin.animate().apply {
                            scaleXBy(-5f)
                            scaleYBy(-5f)
                        }
                    }
                    delay(210)
                    bottleControl()
                    REFRESH(db, (activity as GameActivity).bind, hero)
                }
            }
        }

        bind.btnSpMin.setOnClickListener {
            hero.sp += 40
            litSP -= 1
            scopeDef.launch {
                ForLoot().insertLootDB(db, BottleMpMin(), litSP)
                delay(50)
                bottleCheck(db, (activity as GameActivity).bind, hero)
                SAVE_HERO(db, hero)
                delay(50)
                withContext(Dispatchers.Main) {
                    bind.btnSpMin.animate().apply {
                        duration = 200
                        scaleXBy(5f)
                        scaleYBy(5f)
                    }.withEndAction {
                        bind.btnSpMin.animate().apply {
                            scaleXBy(-5f)
                            scaleYBy(-5f)
                        }
                    }
                    delay(210)
                    bottleControl()
                    REFRESH(db, (activity as GameActivity).bind, hero)
                }
            }
        }

//действие героя
        var atkHero = 0
        var defHero = 0
        //атака
        bind.radHeadAtk.setOnClickListener { atkHero = 1 }
        bind.radChestAtk.setOnClickListener { atkHero = 2 }
        bind.radGroinAtk.setOnClickListener { atkHero = 3 }
        bind.radLegAtk.setOnClickListener { atkHero = 4 }
        //защита
        bind.radDefHead.setOnClickListener { defHero = 1 }
        bind.radDefChest.setOnClickListener { defHero = 2 }
        bind.radDefGroin.setOnClickListener { defHero = 3 }
        bind.radDefLeg.setOnClickListener { defHero = 4 }
//формируем монстра
        var monster: Monster
        monster = MonsterError()
        dataModel.msgMonsterForFight.observe(activity as LifecycleOwner) { monster = it }
        var mobNum = 0
        dataModel.msgNumMonsterForFight.observe(activity as LifecycleOwner) { mobNum = it }

        bind.txtNameMonster.text = monster.name
        bind.txtDiscriptionMonster.text = monster.description
        bind.txtHpMonster.text = monster.hp.toString()
        bind.imgAvaMonster.setImageResource(monster.ava)

        //действия после атаки героем монстра
        fun monsterReact() {
            SAVE_HERO(db, hero)
            //бой окончен
            if (monster.hp <= 0) {
                monster.hp = 0
                bind.logBattle.append("\n бой окончен, ${monster.name} ПОВЕРЖЕН!" +
                        "\n Вы получили ${monster.xp} опыта, и ${monster.coin} золотых")
                monster.drop4Hero(db, bind)
                hero.xp = hero.xp + monster.xp
                hero.coin = hero.coin + monster.coin
                mobNum -= 1
                Hero().exp4lvl(hero)
                dataModel.msgNumMonsterAfterFight.value = mobNum
                SAVE_HERO(db, hero)
                bind.btnAttack.text = "Назад"
                bind.btnAttack.setOnClickListener {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.placeHolder, QuestFragment.newInstance())
                        .commit()
                }

            }
            if (hero.hp <= 0) {
                hero.hp = 0
                bind.logBattle.append("\n бой окончен, ${monster.name} ПОБЕДИЛ!\n вы потеряли ${monster.xp / 3} опыта")
                hero.xp = hero.xp - monster.xp / 3
                bind.btnAttack.text = "Назад"
                SAVE_HERO(db, hero)
                bind.btnAttack.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.placeHolder, QuestFragment.newInstance()).commit()

                    //TODO() дописать действия в случае поражения
                }
            }
            bind.txtHpMonster.text = monster.hp.toString()
            //отображение в ходе боя
            fun upBarInf(bind: ActivityGameBinding) {
                bind.txtHPUpbar.text = hero.hp.toString()
                bind.txtSPUpbar.text = hero.sp.toString()
                bind.txtXPUpbar.text = hero.xp.toString()
                UpBar().colorStatus(bind, hero)
            }
            upBarInf((activity as GameActivity).bind)
        }

        //магическая атака
        fun heroMAtack(valueSP: Int, bonusMatk: Int) {
            if (monster.hp != 0) {
                if (hero.sp >= valueSP) {
                    hero.sp -= valueSP
                    monster.hp -= (hero.matk + bonusMatk)
                    monsterReact()
                } else {
                    bind.logBattle.append("\n Недостаточно манны")
                }
            } else {
                bind.logBattle.append("\n Монстр мертв")
            }
        }
//магия
        bind.mageFire.setOnClickListener {
            bind.mageFire.animate().apply {
                duration = 500
                rotationYBy(720f)
            }.withEndAction {
                bind.mageFire.animate().apply {
                    duration = 0
                    rotationYBy(0f)
                }
            }
            heroMAtack(60, 0)
        }

        bind.mageWatter.setOnClickListener {
                bind.mageWatter.animate().apply {
                    duration = 500
                    rotationYBy(720f)
                }.withEndAction {
                    bind.mageWatter.animate().apply {
                        duration = 0
                        rotationYBy(0f)
                    }
                }
                heroMAtack(70, 10)
        }
        bind.mageEarth.setOnClickListener {
                bind.mageEarth.animate().apply {
                    duration = 500
                    rotationYBy(720f)
                }.withEndAction {
                    bind.mageEarth.animate().apply {
                        duration = 0
                        rotationYBy(0f)
                    }
                }
                heroMAtack(80, 20)
        }
        bind.mageWind.setOnClickListener {
            bind.mageWind.animate().apply {
                duration = 500
                rotationYBy(720f)
            }.withEndAction {
                bind.mageWind.animate().apply {
                    duration = 0
                    rotationYBy(0f)
                }
            }
            heroMAtack(85, 30)
        }

        bind.mageArkane.setOnClickListener {
            bind.mageArkane.animate().apply {
                duration = 500
                rotationYBy(720f)
            }.withEndAction {
                bind.mageArkane.animate().apply {
                    duration = 0
                    rotationYBy(0f)
                }
            }
            heroMAtack(100, 50)
        }

//битва
        bind.btnAttack.setOnClickListener {
            bind.btnAttack.animate().apply {
                duration = 200
                rotationXBy(360f)
            }.start()
            if (atkHero == monster.diceDef) {
                bind.logBattle.append("${monster.name} поставил Блок!\n")
            } else {
                hero.attack(monster)
                bind.logBattle.append("у ${monster.name} осталось ${monster.hp} жизней\n")
            }
            if (monster.diceAtk == defHero) {
                bind.logBattle.append("${hero.name} поставил Блок!\n")
            } else {
                monster.attack(hero)
                bind.logBattle.append("у ${hero.name} осталось ${hero.hp} жизней\n")
            }
            monsterReact()
        }


    }

    companion object {
        @JvmStatic
        fun newInstance() = FightFragment()
    }
}

