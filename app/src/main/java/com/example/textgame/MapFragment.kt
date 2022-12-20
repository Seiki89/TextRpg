package com.example.textgame

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.classes.REFRESH
import com.example.textgame.classes.scopeMain
import com.example.textgame.databinding.FragmentMapBinding
import com.example.textgame.hero.Hero
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapFragment : Fragment() {
    lateinit var bind:FragmentMapBinding
     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
         bind = FragmentMapBinding.inflate(inflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hero = Hero()
        val db = Maindb.heroSetDb(requireActivity())
        scopeMain.launch {
            REFRESH(db, (activity as GameActivity).bind, hero)
            delay(30)
            when (hero.mapLvl) {
                1 -> bind.btnMapLoc01.setBackgroundColor(Color.parseColor("#338DC654"))
                2 -> bind.btnMapLoc02.setBackgroundColor(Color.parseColor("#1EFF5722"))
            }
        }

        bind.btnMapLoc01.setOnClickListener {
            bind.txtInfoMap.text = "Смотровая башня,безпасная зона"
        }

        bind.btnMapLoc02.setOnClickListener {
            bind.txtInfoMap.text = "Довольно крепкий деревянный мостик, ведущий на небольшой остров"
        }


    }

    companion object {
         @JvmStatic
        fun newInstance() = MapFragment()
    }
}