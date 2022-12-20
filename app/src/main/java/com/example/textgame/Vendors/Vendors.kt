package com.example.textgame.Vendors

import com.example.textgame.Drop.*


class Vendors {

    fun listMod(item: DropList) = mapOf(
        "id" to item.id,
        "name" to item.nameItem,
        "img" to item.imgItem,
        "price" to item.price
        )
    val vendor0 = listOf(
        listMod(ErrorDrop()),
        listMod(ErrorDrop())
    )
//в мапЛвл вендор = 1
    val vendor1 = listOf(
        listMod(BottleHpBig()),
        listMod(BottleHpMin()),
        listMod(BottleMpBig()),
        listMod(BottleMpMin()),
        listMod(GoblinEar()),
        listMod(GoblinTooth())
    )



}
