package com.example.textgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import androidx.fragment.app.Fragment
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.databinding.ActivityGameBinding
import com.example.textgame.hero.Hero
import com.example.textgame.hero.UpBar
import kotlinx.coroutines.cancel
import kotlin.coroutines.coroutineContext


class GameActivity : AppCompatActivity() {
    lateinit var bind : ActivityGameBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityGameBinding.inflate(layoutInflater)
        setContentView(bind.root)
//контроль утечек
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
            .detectLeakedClosableObjects()
            .build())

//загрузка игрового фрагмента
        openFrag(QuestFragment.newInstance(), R.id.placeHolder)

//нижнее меню
        bind.bottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.quest -> {
                        openFrag(QuestFragment.newInstance(), R.id.placeHolder)
                    }
                    R.id.hero -> {
                        openFrag(HeroStatFragment.newInstance(), R.id.placeHolder)
                    }
                    R.id.bag -> {
                        openFrag(HeroBagFragment.newInstance(), R.id.placeHolder)
                    }
                    R.id.map -> {
                        openFrag(MapFragment.newInstance(), R.id.placeHolder)
                    }
                }
                true
            }
    }


//работа с фрагментами
    private fun openFrag(f: Fragment, idHolder: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(idHolder, f)
            .commit()
    }
}
