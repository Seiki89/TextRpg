package com.example.textgame.RoomDao


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database (entities = [HeroDb::class, LootDb::class, ReBuy::class], version = 1)
abstract class Maindb : RoomDatabase() {
    abstract fun getDao(): Dao



    companion object{
        fun heroSetDb(context:Context): Maindb{
            return Room.databaseBuilder(
                context.applicationContext,
                Maindb::class.java,
                "DataBaseFile.db").build()
        }
    }
}
