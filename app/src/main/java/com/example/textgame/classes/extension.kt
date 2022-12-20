package com.example.textgame.classes

import android.util.Log
import com.example.textgame.R
import com.example.textgame.hero.Hero
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


val scopeDef = CoroutineScope(Dispatchers.Default)
val scopeMain = CoroutineScope(Dispatchers.Main)

val male = R.string.Male
val female = R.string.Female
val warrior = R.string.Warrior
val thief = R.string.Thief
val mage = R.string.Mage

fun random(value1:Int, value2: Int) = (value1..value2).shuffled().first()
fun chance() = random(1,(100))
fun percent(value:Int, percent:Int) = value*percent/100

fun Log (cont :String){Log.d("MyTag",cont)}

