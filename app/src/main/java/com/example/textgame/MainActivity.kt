package com.example.textgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.textgame.Drop.*
import com.example.textgame.RoomDao.HeroDb
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.classes.*
import com.example.textgame.databinding.ActivityMainBinding
import com.example.textgame.hero.Hero
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val db = Maindb.heroSetDb(this)
        val toGameActivity = Intent(this, GameActivity::class.java)
        var sex = ""
        var prof = ""
        var avatar = 0


//выбор пола
        fun profMaleImg() {
            sex = resources.getString(male)
            bind.imgManWarrior.visibility = View.VISIBLE
            bind.imgManThef.visibility = View.VISIBLE
            bind.imgManMage.visibility = View.VISIBLE
            bind.imgWomanWarrior.visibility = View.INVISIBLE
            bind.imgWomanThef.visibility = View.INVISIBLE
            bind.imgWomanMage.visibility = View.INVISIBLE
            bind.radioProf.visibility = View.VISIBLE
        }
        fun profFemaleImg() {
            sex = resources.getString(female)
            bind.imgWomanWarrior.visibility = View.VISIBLE
            bind.imgWomanThef.visibility = View.VISIBLE
            bind.imgWomanMage.visibility = View.VISIBLE
            bind.imgManWarrior.visibility = View.INVISIBLE
            bind.imgManThef.visibility = View.INVISIBLE
            bind.imgManMage.visibility = View.INVISIBLE
            bind.radioProf.visibility = View.VISIBLE
        }
        bind.butMale.setOnClickListener { profMaleImg() }
        bind.butFamale.setOnClickListener { profFemaleImg() }
//выбор профессии
        bind.radioWarrior.setOnClickListener { prof = resources.getString(warrior) }
        bind.radioThef.setOnClickListener { prof = resources.getString(thief) }
        bind.radioMage.setOnClickListener { prof = resources.getString(mage) }
// фиксация аватара
        fun avaFix() {
            if (sex == resources.getString(male)) {
                if (prof == resources.getString(warrior)) {
                    avatar = 1
                }
                if (prof == resources.getString(thief)) {
                    avatar = 2
                }
                if (prof == resources.getString(mage)) {
                    avatar = 3
                }
            }
            if (sex == resources.getString(female)) {
                if (prof == resources.getString(warrior)) {
                    avatar = 4
                }
                if (prof == resources.getString(thief)) {
                    avatar = 5
                }
                if (prof == resources.getString(mage)) {
                    avatar = 6
                }
            }
        }
//проверка наличия старого героя
        fun getOldName() = scopeDef.launch {
            db.getDao().getHero().toList().forEach {
                val oldHero: String = it.name
                val oldLvl = it.lvl .toString()
                val ava = it.avatar
                withContext(Dispatchers.Main) {
                    bind.txtOldHero.text = oldHero
                    bind.txtOldLvl.text = "${resources.getString(R.string.Level)} $oldLvl"
                    bind.imgProfEmpty.setImageResource(Hero().avaSingle(ava))
                }
            }
        }
        getOldName()
// продожить старую игру
        bind.continium.setOnClickListener {
            if (bind.txtOldHero.text == resources.getString(R.string.EmptyHero)) {
                bind.txtOldHero.text = resources.getString(R.string.EmptyHero)
            } else {
                startActivity(toGameActivity)
                finish()
            }
        }
// создание нового героя
        bind.newStart.setOnClickListener {

            avaFix()
            val heroName = bind.txtInputName.text.toString()
            val newHero = HeroDb(1, avatar, sex, heroName, prof, 50, 50, 2100, 0,
                10, 8, 999,0,0,0,0)
            fun settSave() = scopeDef.launch {
                preLoadDbLoot(db)
                db.getDao().insertHero(newHero)
            }
            if (bind.txtInputName.text.isNotEmpty() && sex.isNotBlank() && prof.isNotBlank()) {
                settSave()
                startActivity(toGameActivity)
                finish()
            } else Toast.makeText(this, resources.getString(R.string.NotFields), Toast.LENGTH_SHORT)
                .show()
        }
    }
}
