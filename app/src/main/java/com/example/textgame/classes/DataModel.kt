package com.example.textgame.classes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.textgame.monsters.Monster

open class DataModel : ViewModel() {
//мобы на карте, бой, результат боя
    val msgMonsterForFight : MutableLiveData<Monster> by lazy {MutableLiveData<Monster>()}
    val msgNumMonsterForFight : MutableLiveData<Int> by lazy {MutableLiveData<Int>()}
    val msgNumMonsterAfterFight : MutableLiveData<Int> by lazy {MutableLiveData<Int>()}

//сообщение для информационного окна  в инвентаре
    val msgEquipIdForInformation : MutableLiveData<List<Int>> by lazy {MutableLiveData<List<Int>>()}
    val msgEquipIdForInformationName : MutableLiveData<List<String>> by lazy {MutableLiveData<List<String>>()}

//выбор у трейдера (1купить 2продать 3выкупить)
    val msgTradeType : MutableLiveData<Int> by lazy {MutableLiveData<Int>()}
    //выбор вендора
    val msgVendorNum : MutableLiveData<Int> by lazy {MutableLiveData<Int>()}




}