package com.hank.emptyapplication

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hank.emptyapplication.data.Card
import com.hank.emptyapplication.data.CardDatabase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MyCardsFragment : Fragment(){
    var listCard = mutableListOf<CardPrefab>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = CardDatabase.getInstance(context!!)
        AsyncTask.execute {
            val allCards = database?.cardDao()?.getAllCards()
            allCards?.forEach {card ->
                if(card.card_my == 1){
                    var lstType = database?.cardDao()?.getCouponType(card.id)
                    val lstDiscount = database?.cardDao()?.getCouponDiscount(card.id)
                    var content = ""
                    for(i in 0..(lstType.size-1)){
                        content = content.plus((lstType[i]).plus(":"))
                        content = content.plus(lstDiscount[i]).plus("\n")
                    }
                    listCard.add(CardPrefab(card.card_img,card.card_name,content))
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_cards,container,false)
        val myRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_my)
        val check = view.findViewById<CheckBox>(R.id.checkBox_oversea)
        val check1 = view.findViewById<CheckBox>(R.id.checkBox_eat)
        myRecyclerView.layoutManager = LinearLayoutManager(activity)
        myRecyclerView.adapter = MyRecyclerViewAdpter(context!!,listCard)

        //Set Listener
        check?.setOnCheckedChangeListener{_, isChecked ->
            listCard.clear()
            val database = CardDatabase.getInstance(context!!)
            if(isChecked){
                doAsync{
                    val lstCard = database?.cardDao()?.getAllCards()
                    lstCard?.forEach { card ->
                        if (card.card_my == 1) {
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
                            }
                        }
                        uiThread { myRecyclerView.adapter?.notifyDataSetChanged() }
                    }
                }
            }else{
                doAsync {
                    val lstCard = database?.cardDao()?.getAllCards()
                    lstCard?.forEach { card ->
                        if (card.card_my == 1) {
                            var lstType = database?.cardDao()?.getCouponType(card.id)
                            val lstDiscount = database?.cardDao()?.getCouponDiscount(card.id)
                            var content = ""
                            for (i in 0..(lstType.size - 1)) {
                                content = content.plus((lstType[i]).plus(":"))
                                content = content.plus(lstDiscount[i]).plus("\n")
                            }
                            listCard.add(CardPrefab(card.card_img, card.card_name, content))
                        }
                    }
                    uiThread { myRecyclerView.adapter?.notifyDataSetChanged() }
                }
            }
        }

        check1?.setOnCheckedChangeListener{_, isChecked ->
            listCard.clear()
            val database = CardDatabase.getInstance(context!!)
            if(isChecked){
                doAsync{
                    val lstCard = database?.cardDao()?.getAllCards()
                    lstCard?.forEach { card ->
                        if (card.card_my == 1) {
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
                            }
                        }
                    }
                    uiThread { myRecyclerView.adapter?.notifyDataSetChanged() }
                }
            }else{
                doAsync {
                    val lstCard = database?.cardDao()?.getAllCards()
                    lstCard?.forEach { card ->
                        if (card.card_my == 1) {
                            var lstType = database?.cardDao()?.getCouponType(card.id)
                            val lstDiscount = database?.cardDao()?.getCouponDiscount(card.id)
                            var content = ""
                            for (i in 0..(lstType.size - 1)) {
                                content = content.plus((lstType[i]).plus(":"))
                                content = content.plus(lstDiscount[i]).plus("\n")
                            }
                            listCard.add(CardPrefab(card.card_img, card.card_name, content))
                        }
                    }
                    uiThread { myRecyclerView.adapter?.notifyDataSetChanged() }
                }
            }
        }


        return view
    }
}

class MyRecyclerViewAdpter(val my_context : Context, val my_data : List<CardPrefab>) : RecyclerView.Adapter<MyRecyclerViewAdpter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(my_context).inflate(R.layout.row_function,parent,false)
        val holder = MyViewHolder(itemView)
        holder.card_img = itemView.findViewById(R.id.img_card)
        holder.card_name = itemView.findViewById(R.id.textView_card_name)
        holder.card_discount = itemView.findViewById(R.id.textView_card_discount)
        return holder
    }

    override fun getItemCount(): Int {
        return my_data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.card_img.setImageResource(my_data.get(position).photo)
        holder.card_name.setText(my_data.get(position).name)
        holder.card_discount.setText(my_data.get(position).content)
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        lateinit var card_img : ImageView
        lateinit var card_name : TextView
        lateinit var card_discount : TextView
    }
}