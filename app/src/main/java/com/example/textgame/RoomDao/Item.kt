package com.example.textgame.RoomDao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
    (tableName = "Hero")
data class HeroDb(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "avatar")
    var avatar: Int,
    @ColumnInfo(name = "sex")
    var sex: String,
    @ColumnInfo(name = "NameHero")
    var name: String,
    @ColumnInfo(name = "prof")
    var prof: String,
    @ColumnInfo(name = "HP")
    var hp: Int,
    @ColumnInfo(name = "SP")
    var sp: Int,
    @ColumnInfo(name = "XP")
    var xp: Int,
    @ColumnInfo(name = "Aura")
    var aura: Int,
    @ColumnInfo(name = "Coin")
    var coin: Int,
    @ColumnInfo(name = "lvl")
    var lvl: Int,
    @ColumnInfo(name = "mapLvl")
    var mapLvl: Int,
    @ColumnInfo(name = "weapon")
    var weapon: Int,
    @ColumnInfo(name = "shield")
    var shield: Int,
    @ColumnInfo(name = "chest")
    var chest: Int,
    @ColumnInfo(name = "leg")
    var leg: Int,
)

@Entity
    (tableName = "Loot")
data class LootDb(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "img")
    val ico: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "atk")
    val atk: Int,
    @ColumnInfo(name = "matk")
    val matk: Int,
    @ColumnInfo(name = "crit")
    val crit: Int,
    @ColumnInfo(name = "dodge")
    val dodge: Int,
    @ColumnInfo(name = "block")
    val block: Int,
    @ColumnInfo(name = "def")
    val def: Int,
    @ColumnInfo(name = "mdef")
    val mdef: Int,
    @ColumnInfo(name = "PTs")
    var PTs: Int,
    @ColumnInfo(name = "price")
    var price: Int,
)

@Entity
    (tableName = "ReBuy")
data class ReBuy(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "img")
    val ico: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "atk")
    val atk: Int,
    @ColumnInfo(name = "matk")
    val matk: Int,
    @ColumnInfo(name = "crit")
    val crit: Int,
    @ColumnInfo(name = "dodge")
    val dodge: Int,
    @ColumnInfo(name = "block")
    val block: Int,
    @ColumnInfo(name = "def")
    val def: Int,
    @ColumnInfo(name = "mdef")
    val mdef: Int,
    @ColumnInfo(name = "PTs")
    var PTs: Int,
    @ColumnInfo(name = "price")
    var price: Int,
)
