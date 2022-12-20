package com.example.textgame.Drop

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.textgame.classes.DataModel
import com.example.textgame.classes.scopeMain
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class MyDialogFragment : DialogFragment() {

    private val dataModel: DataModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var idLoot = 0

        var textList = listOf<String>()
        var statList = listOf<Int>()

        dataModel.msgEquipIdForInformation.observe(activity as LifecycleOwner) { statList = it }
        dataModel.msgEquipIdForInformationName.observe(activity as LifecycleOwner) { textList = it }


        scopeMain.launch { delay(50) }

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            if (textList[1] == "bottle"){
                builder.setTitle(textList[0])
                    .setMessage("\n Recover HP: ${statList[0]} \n Recover MP: ${statList[1]} ")
                    .setIcon(statList[7])
                    .setPositiveButton("ok!") { dialog, id ->
                        dialog.cancel()
                    }
            }else{
                builder.setTitle(textList[0])
                    .setMessage("\n Atk = ${statList[0]} \n MAtk = ${statList[1]} " +
                            "\n Crit = ${statList[2]} \n Dodge = ${statList[3]} " +
                            "\n Block = ${statList[4]} \n Def = ${statList[5]}  " +
                            "\n MDef = ${statList[6]} ")
                    .setIcon(statList[7])
                    .setPositiveButton("ok!") { dialog, id ->
                        dialog.cancel()
                    }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}