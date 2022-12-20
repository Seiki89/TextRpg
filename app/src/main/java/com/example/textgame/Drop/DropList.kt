package com.example.textgame.Drop

import com.example.textgame.R
import com.example.textgame.RoomDao.Maindb

abstract class DropList(
    val id :Int,
    val imgItem : Int,
    val nameItem : String,
    val typeItem : String,
    val atkItem : Int,
    val matkItem : Int,
    val critItem : Int,
    val dodgeItem : Int,
    val blockItem : Int,
    val defItem : Int,
    val mdefItem : Int,
    val price: Int
)

fun preLoadDbLoot(db:Maindb) {
    ForLoot().insertLootDB(db, ErrorDrop(), 0)
    ForLoot().insertLootDB(db, GoblinTooth(), 3)
    ForLoot().insertLootDB(db, GoblinEar(), 3)

    ForLoot().insertLootDB(db, DiabloAxe(), 1)
    ForLoot().insertLootDB(db, DemonKnifeL(), 1)
    ForLoot().insertLootDB(db, DemonKnifeR(), 1)


    ForLoot().insertLootDB(db, BottleHpBig(), 1)
    ForLoot().insertLootDB(db, BottleMpBig(), 1)
    ForLoot().insertLootDB(db, BottleHpMin(), 1)
    ForLoot().insertLootDB(db, BottleMpMin(), 1)


    //TODO()/** заполнить весь лут на дозагрузку при создани нового персонажа*/

}

class ErrorDrop() :DropList (0,
    0,
    "error",
    "error",
    0,0,0,0,0,0,0,0)

class GoblinEar() :DropList(301,
    R.drawable.loot_goblin_ear,
    "Goblin ear",
    "loot",
    0,0,0,0,0,0,0,2)

class GoblinTooth():DropList(302,
    R.drawable.loot_goblin_tooth,
    "Goblin tooth",
    "loot",
    0,0,0,0,0,0,0,3)

class DiabloAxe():DropList(3,
    R.drawable.equip_th_diablo_axe,
    "Diablo Axe",
    "two hand",
    50,10,10,0,0,0,0,500)

class DemonKnifeL():DropList(4,
    R.drawable.equip_oh_demon_knife,
    "Demon knife",
    "one handl",
    20,5,15,10,0,0,0,300)

class DemonKnifeR():DropList(5,
    R.drawable.equip_oh_demon_knife,
    "Demon knife",
    "one handr",
    20,5,15,10,0,0,0,300)


/** Банки для использования*/
class BottleHpBig():DropList(999,
    R.drawable.loot_eat_health_big,
    "Big HP bottle",
    "bottle",
    70,0,0,0,0,0,0,5)

class BottleMpBig():DropList(998,
    R.drawable.loot_eat_manna_big,
    "Big MP bottle",
    "bottle",
    0,70,0,0,0,0,0,4)

class BottleHpMin():DropList(997,
    R.drawable.loot_eat_health_min,
    "Little HP bottle",
    "bottle",
    40,0,0,0,0,0,0,3)

class BottleMpMin():DropList(996,
    R.drawable.loot_eat_manna_min,
    "Little MP bottle",
    "bottle",
    0,40,0,0,0,0,0,2)
