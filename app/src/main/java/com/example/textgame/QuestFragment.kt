package com.example.textgame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.textgame.map.MapLevels
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.classes.REFRESH
import com.example.textgame.classes.scopeMain
import com.example.textgame.databinding.FragmentQuestBinding
import com.example.textgame.hero.Hero
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class QuestFragment : Fragment() {
    lateinit var bind: FragmentQuestBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        bind = FragmentQuestBinding.inflate(inflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hero = Hero()
        val db = Maindb.heroSetDb(requireActivity())

        REFRESH(db, (activity as GameActivity).bind, hero)


//выбор MapLvl
        scopeMain.launch {
            delay(50)
            MapLevels(bind, hero, db, requireActivity()).changeLvl()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = QuestFragment()
    }
}

