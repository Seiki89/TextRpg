package com.example.textgame.hero


import com.example.textgame.R
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.classes.chance
import com.example.textgame.classes.scopeDef
import com.example.textgame.monsters.Fight
import kotlinx.coroutines.*

open class Hero(
    var ava: Int = 0,
    var sex: String = String(),
    var name: String = String(),
    var prof: String = String(),
    override var hp: Int = 1,
    var sp: Int = 0,
    var xp: Int = 0,
    var aura: Int = 0,
    var coin: Int = 0,
    var lvl: Int = 1,
    var mapLvl : Int = 1,
    var weapon : Int = 0,
    var shield : Int = 0,
    var chest : Int = 0,
    var leg : Int = 0,

    override var atk: Int = 0,
    override var matk: Int = 0,
    override var crit: Int = 0,
    override var dodge: Int = 0,
    override var block: Int = 0,
    override var def: Int = 0,
    override var mdef: Int = 0,
    override var maxhp:Int = 0,
    override var maxsp:Int = 0
) : Fight {



    //вызов характеристик из ДБ
    fun extractHeroData(db: Maindb, hero: Hero) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getDao().getHero().toList().forEach {
                    hero.ava = it.avatar
                    hero.sex = it.sex
                    hero.name = it.name
                    hero.prof = it.prof
                    hero.hp = it.hp
                    hero.sp = it.sp
                    hero.xp = it.xp
                    hero.aura = it.aura
                    hero.coin = it.coin
                    hero.lvl = it.lvl
                    hero.mapLvl = it.mapLvl
                    hero.weapon  = it.weapon
                    hero.shield  = it.shield
                    hero.chest  = it.chest
                    hero.leg  = it.leg
            }
        }
    }

    //вывод аватара главного героя
    fun avaSingle(avatar: Int): Int {
        return when (avatar) {
            1 -> R.drawable.prof_man_warrior
            2 -> R.drawable.prof_man_thef
            3 -> R.drawable.prof_man_mage
            4 -> R.drawable.prof_woman_warrior
            5 -> R.drawable.prof_woman_thef
            6 -> R.drawable.prof_woman_mage
            else -> {
                R.drawable.prof_empty
            }
        }
    }

    //определение уровня
    fun exp4lvl(hero: Hero) {
        when (hero.xp){
            in 0..99 -> hero.lvl = 1
            in 100..249 -> hero.lvl = 2
            in 250..399 ->hero.lvl = 3
            in 400..649 -> hero.lvl = 4
            in 650..949 -> hero.lvl = 5
            in 950..1399 -> hero.lvl = 6
            in 1400..1999 -> hero.lvl = 7
            in 2000..2699 ->hero.lvl = 8
            in 2700..3600 -> hero.lvl = 9
            else -> hero.lvl = 10
        }
    }

    //распределение сил
    fun statsProf(hero: Hero) {
        if (hero.prof == "Warrior" || hero.prof == "Воин") {
            hero.atk = 10+(2*lvl)
            hero.matk = 0
            hero.crit = 2+(lvl/2)
            hero.dodge = 2+(lvl/2)
            hero.block = 0
            hero.def = 15+(3*lvl)
            hero.mdef = 5+(lvl/2)
            hero.maxhp = 100+(10*lvl)
            hero.maxsp = 50+(2*lvl)
        }
        if (hero.prof == "Thief" || hero.prof == "Вор") {
            hero.atk = 7+lvl
            hero.matk = 0
            hero.crit = 15+(2*lvl)
            hero.dodge = 15+(2*lvl)
            hero.block = 0
            hero.def = 8+(2*lvl)
            hero.mdef = 8+lvl
            hero.maxhp = 80+(7*lvl)
            hero.maxsp = 75+(5*lvl)
        }
        if (hero.prof == "Mage" || hero.prof == "Маг") {
            hero.atk = 5+(lvl/2)
            hero.matk = 13+(2*lvl)
            hero.crit = 2+lvl
            hero.dodge = 0
            hero.block = 5
            hero.def = 5+lvl/2
            hero.mdef = 15+(2*lvl)
            hero.maxhp = 60+(5*lvl)
            hero.maxsp = 100+(10*lvl)
        }
    }

    //влияние аур
    fun auraActive() {
        if (aura == 1) {
            atk += 5
            def -= 2
            crit += 5
        }
        if (aura == 2) {
            matk += 5
            mdef -= 5
            crit += 5
        }
        if (aura == 3) {
            var deadcoin = 0
            deadcoin += 5
        }
    }

    //Прогрузить статы
    fun statsRefreshLoad(hero: Hero, db: Maindb) {
        hero.extractHeroData(db, hero)
        scopeDef.launch {
            while (hero.name.isBlank()) {
                delay(20)
            }
            hero.exp4lvl(hero)
            hero.statsProf(hero)
            hero.auraActive()
        }
    }

    override fun attack(opponent: Fight): Int {
        fun damageDealt():Int {
            var dmg = damageRoll - (atk * opponent.def / 100)
            if (chance() in 1..crit) {
                dmg *= 2
            }
            if (chance() in 1..dodge) {
                dmg = 0
            }
            if (chance() in 1..block) {
                dmg = 0
            }
            return dmg
        }

        opponent.hp -= damageDealt()
        return damageDealt()
    }



}