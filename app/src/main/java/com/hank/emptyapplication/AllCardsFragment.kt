package com.hank.emptyapplication

import android.app.AlertDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hank.emptyapplication.data.CardDatabase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AllCardsFragment : Fragment() {
    var listCard = mutableListOf<CardPrefab>()
    val TAG = "AllCardsFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "Create")


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "Create view ")
        val view = inflater.inflate(R.layout.fragment_all_cards, container, false)
        val myRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_all)
        val myRecyclerViewAdapter = RecyclerViewAdapter(context!!,listCard)
        val check = view.findViewById<CheckBox>(R.id.check_oversea)
        val check1 = view.findViewById<CheckBox>(R.id.check_eat)
        myRecyclerView.layoutManager = LinearLayoutManager(activity)
        myRecyclerView.adapter = myRecyclerViewAdapter

        val database = CardDatabase.getInstance(context!!)
        doAsync{
            val lstCard = database?.cardDao()?.getAllCards()
            lstCard?.forEach{card ->
                var lstType = database?.cardDao()?.getCouponType(card.id)
                val lstDiscount = database?.cardDao()?.getCouponDiscount(card.id)
                var content = ""
                for(i in 0..(lstType.size-1)){
                    content = content.plus((lstType[i]).plus(":"))
                    content = content.plus(lstDiscount[i]).plus("\n")
                }
                listCard.add(CardPrefab(card.card_img,card.card_name,content))
            }
            uiThread { myRecyclerViewAdapter.notifyDataSetChanged() }
        }

    //Set Listener
        check?.setOnCheckedChangeListener{_, isChecked ->
            listCard.clear()
                if(isChecked){
                    doAsync{
                        val lstCard = database?.cardDao()?.getAllCards()
                        lstCard?.forEach { card ->
                            var exist = false
                            val lstType = database?.cardDao()?.getCouponType(card.id)
                            lstType.forEach {
                                if (it == "海外") {
                                    exist = true
                                }
                            }
                            if (exist) {
                                val lstDiscount = database?.cardDao()?.getCouponDiscount(card.id)
                                var content = ""
                                for (i in 0..(lstType.size - 1)) {
                                    content = content.plus((lstType[i]).plus(":"))
                                    content = content.plus(lstDiscount[i]).plus("\n")
                                }
                                listCard.add(CardPrefab(card.card_img, card.card_name, content))
                                Log.d(TAG, "Exist size: ${listCard.size} ");
                            }
                        }
                        uiThread { myRecyclerViewAdapter.notifyDataSetChanged() }
                    }
                }else{
                    doAsync {
                        val lstCard = database?.cardDao()?.getAllCards()
                        lstCard?.forEach{card ->
                            var lstType = database?.cardDao()?.getCouponType(card.id)
                            val lstDiscount = database?.cardDao()?.getCouponDiscount(card.id)
                            var content = ""
                            for(i in 0..(lstType.size-1)){
                                content = content.plus((lstType[i]).plus(":"))
                                content = content.plus(lstDiscount[i]).plus("\n")
                            }
                            listCard.add(CardPrefab(card.card_img,card.card_name,content))
                        }
                        uiThread { myRecyclerViewAdapter.notifyDataSetChanged() }
                    }
                }
        }

        check1?.setOnCheckedChangeListener{_, isChecked ->
            listCard.clear()
            if(isChecked){
                doAsync{
                    val lstCard = database?.cardDao()?.getAllCards()
                    lstCard?.forEach { card ->
                        var exist = false
                        val lstType = database?.cardDao()?.getCouponType(card.id)
                        lstType.forEach {
                            if (it == "吃飯") {
                                exist = true
                            }
                        }
                        if (exist) {
                            val lstDiscount = database?.cardDao()?.getCouponDiscount(card.id)
                            var content = ""
                            for (i in 0..(lstType.size - 1)) {
                                content = content.plus((lstType[i]).plus(":"))
                                content = content.plus(lstDiscount[i]).plus("\n")
                            }
                            listCard.add(CardPrefab(card.card_img, card.card_name, content))
                            Log.d(TAG, "Exist size: ${listCard.size} ");
                        }
                    }
                    uiThread { myRecyclerViewAdapter.notifyDataSetChanged() }
                }
            }else{
                doAsync {
                    val lstCard = database?.cardDao()?.getAllCards()
                    lstCard?.forEach{card ->
                        var lstType = database?.cardDao()?.getCouponType(card.id)
                        val lstDiscount = database?.cardDao()?.getCouponDiscount(card.id)
                        var content = ""
                        for(i in 0..(lstType.size-1)){
                            content = content.plus((lstType[i]).plus(":"))
                            content = content.plus(lstDiscount[i]).plus("\n")
                        }
                        listCard.add(CardPrefab(card.card_img,card.card_name,content))
                    }
                    uiThread { myRecyclerViewAdapter.notifyDataSetChanged() }
                }
            }
        }
        return view
    }

}


class CardPrefab(val photo : Int , val name : String,val content : String)

class RecyclerViewAdapter(val mContext : Context,val mData : List<CardPrefab>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_function,parent,false)
        val mViewHolder = MyViewHolder(view)
        mViewHolder.card_img = view.findViewById(R.id.img_card)
        mViewHolder.card_name = view.findViewById(R.id.textView_card_name)
        mViewHolder.card_discount = view.findViewById(R.id.textView_card_discount)
        return mViewHolder
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.card_img.setImageResource(mData.get(position).photo)
        holder.card_name.setText(mData.get(position).name)
        holder.card_discount.setText(mData.get(position).content)
        holder.itemView.setOnClickListener{
            onAllCardClicked(position)
        }
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        lateinit var card_img : ImageView
        lateinit var card_name : TextView
        lateinit var card_discount : TextView
    }

    private fun onAllCardClicked(position: Int){
        val database = CardDatabase.getInstance(mContext)
        AlertDialog.Builder(mContext)
            .setTitle("新增卡片")
            .setMessage("是否要將卡片新增為我的卡片?")
            .setPositiveButton("是",{dialog, which ->
                AsyncTask.execute {
                    database?.cardDao()?.setCardMy(position)
                    val cardMy = database?.cardDao()?.getAllCards()?.get(0)?.card_my
                    Log.d("AllCardsFragment", "$cardMy")
                }
            })
            .setNegativeButton("否",null)
            .show()
    }
}

class temp{

}


