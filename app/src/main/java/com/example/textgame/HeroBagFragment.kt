package com.example.textgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.textgame.Drop.ForLoot
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.classes.scopeDef
import com.example.textgame.classes.scopeMain
import com.example.textgame.databinding.FragmentHeroBagBinding
import com.example.textgame.hero.Hero
import com.example.textgame.hero.HeroBag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HeroBagFragment : Fragment() {
    lateinit var bind: FragmentHeroBagBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

    ): View {
        bind = FragmentHeroBagBinding.inflate(inflater)
        return bind.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = Maindb.heroSetDb(requireActivity())
        val hero = Hero()
        //загрузка аватара
        scopeMain.launch {
            hero.extractHeroData(db, hero)
            delay(30)
            bind.imgAva.setImageResource(Hero().avaSingle(hero.ava))
        }
        //загрузка из дб в ресайкл
        ForLoot().loadFromDBtoRecycle(db, bind, requireActivity())


        fun statsRefresh() {
            bind.txtATK.text = hero.atk.toString()
            bind.txtMATK.text = hero.matk.toString()
            bind.txtCRIT.text = hero.crit.toString()
            bind.txtDODGE.text = hero.dodge.toString()
            bind.txtBLOCK.text = hero.block.toString()
            bind.txtDEF.text = hero.def.toString()
            bind.txtMDEF.text = hero.mdef.toString()
        }

        fun start() {
            scopeDef.launch {
                Hero().statsRefreshLoad(hero, db)
                delay(30)
                withContext(Dispatchers.Main) {
                    HeroBag().applyEquipPic(db, bind)
                    HeroBag().applyEquipStat(db, hero)
                    delay(30)
                    statsRefresh()

                }
            }
        }
        start()

    }

    companion object {
        @JvmStatic
        fun newInstance() = HeroBagFragment()
    }
}
