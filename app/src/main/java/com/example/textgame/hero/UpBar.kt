package com.example.textgame.hero

import android.graphics.Color
import android.view.View
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.classes.percent
import com.example.textgame.classes.scopeMain
import com.example.textgame.databinding.ActivityGameBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class UpBar {

    fun statCompl(db: Maindb, bind: ActivityGameBinding, hero: Hero) {
        hero.extractHeroData(db, hero)
        scopeMain.launch {
            while (hero.name.isBlank()) {
                delay(20)
            }
            upBar(bind, hero)
            colorStatus(bind, hero)
        }
    }

    //собрать верхний бар
    fun upBar(bind: ActivityGameBinding, hero: Hero) {
        bind.txtHPUpbar.text = hero.hp.toString()
        bind.txtSPUpbar.text = hero.sp.toString()
        bind.txtXPUpbar.text = hero.xp.toString()
        bind.txtAuraUpbar.text = hero.aura.toString()
        bind.txtCoinUpbar.text = hero.coin.toString()
        if (hero.aura == 0) {
            bind.txtAuraUpbar.visibility = View.INVISIBLE
            bind.imgIcoAura.visibility = View.INVISIBLE
        }
        if (hero.aura == 1) {
            bind.txtAuraUpbar.text = "Агрессия"
        }
        if (hero.aura == 2) {
            bind.txtAuraUpbar.text = "Мысли"
        }
        if (hero.aura == 3) {
            bind.txtAuraUpbar.text = "Жадность"
        }
    }

    //цвета жизней и манны UpBar
    fun colorStatus(bind: ActivityGameBinding, hero: Hero) {
        val hpColor = bind.txtHPUpbar
        val spColor = bind.txtSPUpbar

        val x65 = percent(hero.maxhp,65)
        val x100 = percent(hero.maxhp,100)
        val x45 = percent(hero.maxhp,45)
        val x64 = percent(hero.maxhp,64)
        val x0 = percent(hero.maxhp,0)
        val x44 = percent(hero.maxhp,44)

        val xx65 = percent(hero.maxsp,65)
        val xx100 = percent(hero.maxsp,100)
        val xx45 = percent(hero.maxsp,45)
        val xx64 = percent(hero.maxsp,64)
        val xx0 = percent(hero.maxsp,0)
        val xx44 = percent(hero.maxsp,44)

        when (hero.hp) {
            in x65..x100 -> hpColor.setTextColor(Color.parseColor("#216000"))
            in x45..x64 -> hpColor.setTextColor(Color.parseColor("#989600"))
            in x0..x44 -> hpColor.setTextColor(Color.parseColor("#730000"))
        }
        when (hero.sp) {
            in xx65..xx100 -> spColor.setTextColor(Color.parseColor("#216000"))
            in xx45..xx64 -> spColor.setTextColor(Color.parseColor("#989600"))
            in xx0..xx44 -> spColor.setTextColor(Color.parseColor("#730000"))
        }
    }

}