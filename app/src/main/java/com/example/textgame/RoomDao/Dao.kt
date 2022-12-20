package com.example.textgame.RoomDao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface Dao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertHero(vararg heroDb:HeroDb)
    @Query("SELECT * FROM Hero")
    fun getHero(): List<HeroDb>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertLoot(vararg lootDb: LootDb)
    @Query("SELECT * FROM Loot")
    fun getLoot(): List<LootDb>
    @Delete
    fun delLoot(lootDb: LootDb)
    @Update
    fun updLoot(lootDb: LootDb)
    @Query("SELECT (SELECT COUNT (*) FROM Loot) == 0")
    fun isEmpty(): Boolean

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertReBuy(vararg reBuy: ReBuy)
    @Query("SELECT * FROM ReBuy")
    fun getReBuy(): List<ReBuy>


}