package com.example.textgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.textgame.RoomDao.HeroDb
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.classes.REFRESH
import com.example.textgame.classes.scopeDef
import com.example.textgame.classes.scopeMain
import com.example.textgame.databinding.FragmentHeroStatBinding
import com.example.textgame.hero.Hero
import com.example.textgame.hero.UpBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HeroStatFragment : Fragment() {
    lateinit var bind: FragmentHeroStatBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        bind = FragmentHeroStatBinding.inflate(inflater)
        return bind.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = Maindb.heroSetDb(requireActivity())
        val hero = Hero()


        //обновление статов при вызове
        fun statsRefresh() {
            bind.txtAtk.text = hero.atk.toString()
            bind.txtMAtk.text = hero.matk.toString()
            bind.txtCrit.text = hero.crit.toString()
            bind.txtDodge.text = hero.dodge.toString()
            bind.txtBlock.text = hero.block.toString()
            bind.txtDef.text = hero.def.toString()
            bind.txtMDef.text = hero.mdef.toString()
        }
//стартовая загрузка
        REFRESH(db,(activity as GameActivity).bind,hero)
        scopeMain.launch { delay(50); statsRefresh() }

        //сохранение данных о ауре
        fun auraSave(auraSv: Int) {
            scopeDef.launch {
                db.getDao().insertHero(HeroDb
                    (1, hero.ava, hero.sex, hero.name, hero.prof, hero.hp,
                    hero.sp, hero.xp, auraSv, hero.coin, hero.lvl, hero.mapLvl,hero.weapon,
                    hero.shield,hero.chest,hero.leg))
                withContext(Dispatchers.Main){
                    UpBar().statCompl(db, (activity as GameActivity).bind, hero)
                }
            }
        }

        //загрузка аватара, характеристик и статов
        fun interLoad() {
            hero.statsRefreshLoad(hero, db)
            scopeMain.launch {
                    while (hero.name.isBlank()) {
                        delay(20)
                    }
                    bind.imgHeroProf.setImageResource(Hero().avaSingle(hero.ava))
                    bind.txtName.text = hero.name
                    bind.txtProf.text = hero.prof
                    bind.txtSex.text = hero.sex

                    bind.txtHP.text = "${hero.hp}"
                    bind.txtSP.text = "${hero.sp}"
                    bind.txtXP.text = "${hero.xp}"
                    bind.txtCoin.text = "${hero.coin}"
                    bind.txtLvl.text = "${hero.lvl}"
            }
        }
        interLoad()


        //переключение аур
        fun auraAction(auraInt: Int) {
            auraSave(auraInt)
            hero.aura = auraInt
            REFRESH(db,(activity as GameActivity).bind,hero)
            statsRefresh()
            (activity as GameActivity).bind.txtAuraUpbar.visibility = View.VISIBLE
            (activity as GameActivity).bind.imgIcoAura.visibility = View.VISIBLE
        }

        //кнопки аур
        bind.radioBattle.setOnClickListener {
            auraAction(1)
        }
        bind.radioMagic.setOnClickListener {
            auraAction(2)
        }
        bind.radioGold.setOnClickListener {
            auraAction(3)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = HeroStatFragment()
    }
}
