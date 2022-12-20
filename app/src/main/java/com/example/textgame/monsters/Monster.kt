package com.example.textgame.monsters

import com.example.textgame.Drop.*
import com.example.textgame.R
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.classes.Log
import com.example.textgame.classes.chance
import com.example.textgame.classes.random
import com.example.textgame.classes.scopeDef
import com.example.textgame.databinding.FragmentFightBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//интерфейс боя
interface Fight {

    var hp: Int
    var atk: Int
    var matk: Int
    var crit: Int
    var dodge: Int
    var block: Int
    var def: Int
    val mdef: Int
    val maxhp: Int
    val maxsp: Int

    val diceAtk: Int
        get() = random(1, 4)
    val diceDef: Int
        get() = random(1, 4)
    val damageRoll: Int
        get() {
            var dmg = (atk - (atk * 15 / 100)..atk).random()
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

    fun attack(opponent: Fight): Int
}

// шаблон монстра
abstract class Monster(
    val ava: Int,
    val name: String,
    val description: String,
    override var hp: Int,
    override var atk: Int,
    override var matk: Int,
    override var crit: Int,
    override var dodge: Int,
    override var block: Int,
    override var def: Int,
    override var mdef: Int,
    override var maxhp: Int,
    override var maxsp: Int,
    val xp: Int,
    val coin: Int,
    val drop1: DropList,
    val drop2: DropList,
    val drop3: DropList,
    val drop4: DropList,
    val drop5: DropList,
) : Fight {


    override fun attack(opponent: Fight): Int {
        val damageDealt = damageRoll - (atk * opponent.def / 100)
        opponent.hp -= damageDealt
        return damageDealt
    }

    //дроп с монстра
    fun drop4Hero(db: Maindb, bind: FragmentFightBinding) {
        val drop11 = random(1, 2)
        if (drop11 == 1) {
            scopeDef.launch {
                db.getDao().getLoot().toList().forEach() {
                    if (it.id == drop1.id) {
                        withContext(Dispatchers.Main) {
                            if (it.name == "error") {
                                Log("errorDrop")
                            } else {
                                ForLoot().insertLootDB(db, drop1, it.PTs + 1)
                                bind.logBattle.append("\n Выпал 1 ${drop1.nameItem} ")
                            }
                        }
                    }
                }
            }
        }
        val drop22 = random(1, 3)
        if (drop22 == 1) {
            scopeDef.launch {
                db.getDao().getLoot().toList().forEach() {
                    if (it.id == drop2.id) {
                        withContext(Dispatchers.Main) {
                            if (it.name == "error") {
                                Log("errorDrop")
                            } else {
                                ForLoot().insertLootDB(db, drop2, it.PTs + 1)
                                bind.logBattle.append("\n Выпал 1 ${drop2.nameItem} ")
                            }
                        }
                    }
                }
            }
        }
        val drop33 = random(1, 4)
        if (drop33 == 1) {
            scopeDef.launch {
                db.getDao().getLoot().toList().forEach() {
                    if (it.id == drop3.id) {
                        withContext(Dispatchers.Main) {
                            if (it.name == "error") {
                                Log("errorDrop")
                            } else {
                                ForLoot().insertLootDB(db, drop3, it.PTs + 1)
                                bind.logBattle.append("\n Выпал 1 ${drop3.nameItem} ")
                            }
                        }
                    }
                }
            }
        }
        val drop44 = random(1, 5)
        if (drop44 == 1) {
            scopeDef.launch {
                db.getDao().getLoot().toList().forEach() {
                    if (it.id == drop4.id) {
                        withContext(Dispatchers.Main) {
                            if (it.name == "error") {
                                Log("errorDrop")
                            } else {
                                ForLoot().insertLootDB(db, drop4, it.PTs + 1)
                                bind.logBattle.append("\n Выпал 1 ${drop4.nameItem} ")
                            }
                        }
                    }
                }
            }
        }
        val drop55 = random(1, 2)
        if (drop55 == 1) {
            scopeDef.launch {
                db.getDao().getLoot().toList().forEach() {
                    if (it.id == drop5.id) {
                        withContext(Dispatchers.Main) {
                            if (it.name == "error") {
                                Log("errorDrop")
                            } else {
                                ForLoot().insertLootDB(db, drop5, it.PTs + 1)
                                bind.logBattle.append("\n Выпал 1 ${drop5.nameItem} ")
                            }
                        }
                    }
                }
            }
        }
    }
}

//Монстры:
class MonsterError(
    ava: Int = R.drawable.ic_error,
    name: String = "error_name",
    description: String = "error_description",
) : Monster(ava, name, description, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0,0,0, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Goblin(
    ava: Int = R.drawable.mob_goblin,
    name: String = "Гоблин-вор",
    description: String = "Мерзкий на вид гоблин с кинжалом",
) : Monster(ava, name, description, 30, 6, 0, 1, 15, 0, 3, 0,200,200,5, 5,
    GoblinEar(),
    GoblinTooth(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}


class Goblin_Warrior(
    ava: Int = R.drawable.mob_goblin_warrior,
    name: String = "Гоблин-воин",
    description: String = "Мерзкий на вид гоблин с мечем и щитом",
) : Monster(ava, name, description, 40, 6, 0, 1, 1, 10, 10, 0,200,200, 7, 5, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Goblin_Mage(
    ava: Int = R.drawable.mob_goblin_mage,
    name: String = "Гоблин-маг",
    description: String = "Мерзкий на вид гоблин в странных одеждах",
) : Monster(ava, name, description, 40, 2, 10, 1, 0, 0, 0, 25,200,200, 7, 5, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Orc_Warrior(
    ava: Int = R.drawable.mob_orc_warrior,
    name: String = "Орк-воин",
    description: String = "Туповатого вида орк с шестом и щитом",
) : Monster(ava, name, description, 60, 10, 0, 1, 1, 15, 17, 0,200,200, 13, 7, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Skelet_Warrior(
    ava: Int = R.drawable.mob_skelet_warrior,
    name: String = "Скелет-воин",
    description: String = "Натурального вида - оживший скелет с мечем и щитом",
) : Monster(ava, name, description, 65, 12, 0, 1, 10, 15, 17, 0,200,200, 15, 10, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Giant(
    ava: Int = R.drawable.mob_velikan,
    name: String = "Великан",
    description: String = "Огромный, толстый великан.",
) : Monster(ava, name, description, 80, 17, 0, 0, 0, 0, 0, 0,200,200, 17, 13, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Goat(
    ava: Int = R.drawable.mob_kozel,
    name: String = "Козел",
    description: String = "Пугающего вида человекообразный козел",
) : Monster(ava, name, description, 70, 16, 5, 5, 5, 0, 10, 5,200,200, 17, 14, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Werewolf(
    ava: Int = R.drawable.mob_wolf_thef,
    name: String = "оборотень-вор",
    description: String = "Мускулистый оборотень с мечем и топором",
) : Monster(ava, name, description, 85, 20, 0, 15, 15, 0, 10, 0,200,200, 20, 17, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Werewolf_Mage(
    ava: Int = R.drawable.mob_wolf_mage,
    name: String = "оборотень-маг",
    description: String = "Мускулистый оборотень с магической аурой",
) : Monster(ava, name, description, 85, 5, 20, 15, 0, 0, 5, 20,200,200, 20, 17, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Centaur(
    ava: Int = R.drawable.mob_kentavr,
    name: String = "Кентавр",
    description: String = "большой,мощный, агрессивно настроенный, вооруженный кентавр",
) : Monster(ava, name, description, 90, 23, 0, 10, 10, 0, 20, 15,200,200, 25, 20, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Pumpkin(
    ava: Int = R.drawable.mob_pumpkin,
    name: String = "Пампкин",
    description: String = "Тыквоголовый призрак, встреча с ним не сулит ничего хорошего",
) : Monster(ava, name, description, 93, 25, 10, 15, 15, 5, 20, 25,200,200, 28, 23, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Dragon(
    ava: Int = R.drawable.mob_yasher,
    name: String = "Ящер",
    description: String = "Человкообразный ящер, порвет голыми руками на лоскуты",
) : Monster(ava, name, description, 95, 30, 0, 15, 20, 5, 15, 25,200,200, 30, 27, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Dead(
    ava: Int = R.drawable.mob_dead,
    name: String = "Зомби",
    description: String = "Воскресший, ходячий труп, воняет, не лучше чем выглядит",
) : Monster(ava, name, description, 98, 30, 5, 15, 20, 5, 17, 25,200,200, 33, 30, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Undead(
    ava: Int = R.drawable.mob_unded,
    name: String = "Зомби-воин",
    description: String = "Воскресший, ходячий труп, воняет, не лучше чем выглядит",
) : Monster(ava, name, description, 100, 33, 5, 17, 23, 7, 20, 25,200,200, 36, 30, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Familiar(
    ava: Int = R.drawable.mob_familiar,
    name: String = "Летучая мышь",
    description: String = "Зомбиобразная летучая мышь",
) : Monster(ava, name, description, 105, 30, 5, 20, 30, 0, 15, 30,200,200, 38, 35, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Frog(
    ava: Int = R.drawable.mob_jaba,
    name: String = "Жаба",
    description: String = "Склизкая толстая жаба с молотом",
) : Monster(ava, name, description, 110, 33, 5, 15, 10, 0, 20, 30,200,200, 40, 37, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Gin_Mage(
    ava: Int = R.drawable.mob_jin_mage,
    name: String = "Джин0маг",
    description: String = "непонятная субстанция, похожая на человека",
) : Monster(ava, name, description, 100, 15, 40, 15, 10, 0, 15, 40,200,200, 43, 40, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Succubus(
    ava: Int = R.drawable.mob_sukkub,
    name: String = "Суккуб",
    description: String = "привлекательного вида демон, манит тебя пальчиком к себе",
) : Monster(ava, name, description, 115, 35, 15, 15, 15, 5, 25, 30,200,200, 46, 43, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Succubus_Mage(
    ava: Int = R.drawable.mob_sukkub_mage,
    name: String = "Суккуб-маг",
    description: String = "привлекательного вида демон, искрится, манит тебя пальчиком к себе",
) : Monster(ava, name, description, 117, 15, 40, 15, 15, 5, 15, 45,200,200, 47, 45, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Diablo(
    ava: Int = R.drawable.mob_boss_diablo,
    name: String = "Дьявол",
    description: String = "ну что тут сказать... натурального вида - Дьявол",
) : Monster(ava, name, description, 150, 45, 35, 25, 25, 15, 40, 50,200,200, 100, 100, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}

class Demon_Boss(
    ava: Int = R.drawable.mob_boss_demon,
    name: String = "Дьявол",
    description: String = "Старший демон",
) : Monster(ava, name, description, 150, 60, 50, 25, 25, 25, 50, 50,200,200, 200, 250, ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop(),
    ErrorDrop()) {
    override val diceAtk: Int
        get() = super.diceAtk
    override val diceDef: Int
        get() = super.diceDef
}





