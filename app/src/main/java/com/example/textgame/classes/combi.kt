package com.example.textgame.classes


import androidx.lifecycle.ViewModel
import com.example.textgame.RoomDao.HeroDb
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.databinding.ActivityGameBinding
import com.example.textgame.hero.Hero
import com.example.textgame.hero.HeroBag
import com.example.textgame.hero.UpBar
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext


fun REFRESH(db:Maindb, bind: ActivityGameBinding, hero: Hero){
    scopeMain.launch {
        hero.statsRefreshLoad(hero, db) //загрузка характерстик,стат,уровня, ауры,
        delay(35)
        HeroBag().applyEquipStat(db, hero) // применить эффект еквипа
        delay(35)
        UpBar().statCompl(db, bind, hero) //загрузка и показ верхнего бара
    }



}

fun SAVE_HERO(db:Maindb, hero: Hero){
    val saveHero = HeroDb (1,hero.ava,hero.sex,hero.name,hero.prof,hero.hp,hero.sp,hero.xp,
        hero.aura,hero.coin,hero.lvl,hero.mapLvl,hero.weapon,hero.shield,hero.chest,hero.leg)
    scopeDef.launch {
        db.getDao().insertHero(saveHero)
    }
}

fun bottleCheck(db: Maindb,bind: ActivityGameBinding, hero: Hero){
    if (hero.hp > hero.maxhp){hero.hp = hero.maxhp}
    if (hero.hp == 0){hero.hp = 0}
    if (hero.sp > hero.maxsp){hero.sp = hero.maxsp}
    UpBar().statCompl(db, bind, hero)
}

fun bottleCheck2(hero: Hero){
    //hero.statsProf(hero)
    if (hero.hp > hero.maxhp){hero.hp = hero.maxhp}
    if (hero.hp == 0){hero.hp = 0}
    if (hero.sp > hero.maxsp){hero.sp = hero.maxsp}
    //UpBar().upBar(bind, hero)
    //UpBar().colorStatus(bind, hero)
}