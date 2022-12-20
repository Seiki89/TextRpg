package com.example.textgame.hero

import com.example.textgame.RoomDao.Maindb
import com.example.textgame.classes.scopeDef
import com.example.textgame.databinding.FragmentHeroBagBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HeroBag {
    //применить изображения еквипа
    fun applyEquipPic(db: Maindb, bind: FragmentHeroBagBinding) {
        var weaponID = 0
        var shieldID = 0
        var chestID = 0
        var legID = 0
        scopeDef.launch {
            db.getDao().getHero().toList().forEach {
                withContext(Dispatchers.Main) {
                    weaponID = it.weapon
                    shieldID = it.shield
                    chestID = it.chest
                    legID = it.leg
                    delay(30)
                }
            }
            db.getDao().getLoot().toList().forEach {
                withContext(Dispatchers.Main) {
                    if (weaponID != 0) {
                        if (it.type == "two hand" && it.id == weaponID) {
                            bind.imgWeapon.setImageResource(it.ico)
                            bind.imgWeaponShield.setImageResource(it.ico)
                        }

                        if (it.type == "one handl" && it.id == weaponID) {
                            bind.imgWeapon.setImageResource(it.ico)
                        }
                    }
                    if (shieldID != 0) {
                        if (it.type == "one handr" && it.id == shieldID) {
                            bind.imgWeaponShield.setImageResource(it.ico)
                        }
                    }
                    if (chestID != 0) {
                        if (it.type == "chest" && it.id == shieldID) {
                            bind.imgChest.setImageResource(it.ico)
                        }
                    }
                    if (legID != 0) {
                        if (it.type == "leg" && it.id == shieldID) {
                            bind.imgLeg.setImageResource(it.ico)
                        }
                    }
                }
            }
        }
    }
    fun applyEquipStat(db: Maindb, hero: Hero) {
        scopeDef.launch {
            val equipID = mutableListOf<Int>()
            db.getDao().getHero().toList().forEach {
                equipID.add(it.weapon)
                if (it.weapon == it.shield){it.shield = 0}
                equipID.add(it.shield)
                equipID.add(it.chest)
                equipID.add(it.leg)
            }
            db.getDao().getLoot().toList().forEach() {
                for (eqID in equipID) {
                    if (eqID == it.id) {
                        hero.atk += it.atk
                        hero.matk += it.matk
                        hero.crit += it.crit
                        hero.dodge += it.dodge
                        hero.block += it.block
                        hero.def += it.def
                        hero.mdef += it.mdef
                    }
                }
            }
        }
    }

}