package com.example.textgame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.textgame.Drop.LootAdapter
import com.example.textgame.RoomDao.Maindb
import com.example.textgame.Vendors.ForVendors
import com.example.textgame.Vendors.VendorAdapter
import com.example.textgame.classes.DataModel
import com.example.textgame.classes.Log
import com.example.textgame.databinding.FragmentVendorTradeBinding
import com.example.textgame.hero.Hero


class VendorTradeFragment : Fragment() {
    lateinit var bind: FragmentVendorTradeBinding
    private val dataModel: DataModel by activityViewModels()
    var vendorNum = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        bind = FragmentVendorTradeBinding.inflate(layoutInflater)
        return bind.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = Maindb.heroSetDb(requireActivity())
        val hero = Hero()
        var typeAction = 0
        dataModel.msgTradeType.observe(activity as LifecycleOwner) { typeAction = it }
        dataModel.msgVendorNum.observe(activity as LifecycleOwner) { vendorNum = it }

        if (typeAction == 1) {
            ForVendors().loadFromDbToVendorSellRecycle(db, hero, bind, activity as FragmentActivity, this)
            bind.txtHeader.text = "ПОКУПКА"
        }
        if (typeAction == 2) {
            ForVendors().loadFromDbToVendorBuyRecycle(db, hero, bind, activity as FragmentActivity)
            bind.txtHeader.text = "ПРОДАЖА"
        }
        if (typeAction == 3){
            ForVendors().loadFromDbToVendorReBuyRecycle(db, hero, bind, activity as FragmentActivity)
            bind.txtHeader.text = "ВЫКУП"

        }

        bind.btnBack.setOnClickListener {

            (activity as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.placeHolder, QuestFragment.newInstance())
                .commit()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = VendorTradeFragment()
    }
}
