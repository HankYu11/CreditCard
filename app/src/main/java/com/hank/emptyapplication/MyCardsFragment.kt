package com.hank.emptyapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hank.emptyapplication.data.CardDatabase
import kotlinx.android.synthetic.main.tag_fuction.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MyCardsFragment : Fragment(){
    val TAG = "MyCardsFragment"

    var listCard = mutableListOf<CardPrefab>()
    lateinit var database : CardDatabase
    lateinit var myRecyclerView : RecyclerView
    lateinit var typeList : List<String>
    lateinit var myView : View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_my_cards,container,false)

        database = CardDatabase.getInstance(context!!)!!
        doAsync{
            //Get Lists from database
            val cardsList = database.cardDao().getAllCards()
            val typeSet = mutableSetOf<String>()
            cardsList.forEach {card ->
                if(card.card_my == 1){
                    database.cardDao().getCouponType(card.id).forEach{
                        typeSet.add(it)
                    }
                }
            }
            typeList = typeSet.toList()
            //生成完typeList後才生成tagRecyclerView
            uiThread {
                //Tag recyclerView
                val tagRecyclerView = myView.findViewById<RecyclerView>(R.id.recycler_tag_my)
                val tagLayoutManager = LinearLayoutManager(activity)
                tagLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                tagRecyclerView.layoutManager = tagLayoutManager
                tagRecyclerView.adapter = TagRecyclerAdapter(typeList)
            }
        }

        //Card recyclerView
        myRecyclerView = myView.findViewById<RecyclerView>(R.id.recycler_card_my)
        myRecyclerView.layoutManager = LinearLayoutManager(activity)
        myRecyclerView.adapter = MyRecyclerViewAdapter(context!!,listCard)

        //OnCreateView Card初始化
        doAsync {
            val cardsList = database.cardDao().getAllCards()
            cardsList.forEach {card ->
                if(card.card_my == 1){
                    val lstType = database.cardDao().getCouponType(card.id)
                    val lstDiscount = database.cardDao().getCouponDiscount(card.id)
                    var content = ""
                    for(i in 0..(lstType.size-1)){
                        content = content.plus((lstType[i]).plus(":"))
                        content = content.plus(lstDiscount[i]).plus("\n")
                    }
                    listCard.add(CardPrefab(card.card_img,card.card_name,content))
                }
            }
            uiThread {
                myRecyclerView.adapter?.notifyDataSetChanged()
                val textCardSize = myView.findViewById<TextView>(R.id.text_cardSize)
                textCardSize.text = "(${listCard.size})"}

        }

        return myView
    }

    inner class TagRecyclerAdapter (val tag_data : List<String>):RecyclerView.Adapter<TagViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
            return TagViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tag_fuction,parent,false))
        }

        override fun getItemCount(): Int {
            return tag_data.size
        }

        override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
            val tagName = tag_data[position]
            holder.tag.text = tag_data[position]
            holder.tag.setOnCheckedChangeListener{_, isChecked ->
                tagClicked(isChecked,tagName)
            }
        }
    }

    private fun tagClicked(isChecked : Boolean,tagName: String) {
        listCard.clear()
            if(isChecked) {
                doAsync {
                    val cardsList = database.cardDao().getAllCards()
                    cardsList.forEach { card ->
                        if (card.card_my == 1) {
                            var exist = false
                            val lstType = database.cardDao().getCouponType(card.id)
                            lstType.forEach {
                                if (it == tagName) {
                                    exist = true
                                }
                            }
                            if (exist) {
                                val lstDiscount = database.cardDao().getCouponDiscount(card.id)
                                var content = ""
                                for (i in 0..(lstType.size - 1)) {
                                    content = content.plus((lstType[i]).plus(":"))
                                    content = content.plus(lstDiscount[i]).plus("\n")
                                }
                                listCard.add(CardPrefab(card.card_img, card.card_name, content))
                            }
                        }
                    }
                    uiThread {
                        myRecyclerView.adapter?.notifyDataSetChanged()
                        val textCardSize = myView.findViewById<TextView>(R.id.text_cardSize)
                        textCardSize.text = "(${listCard.size} result)"
                    }
                }
            }else{
                doAsync {
                    val cardsList = database.cardDao().getAllCards()
                    cardsList.forEach { card ->
                        if (card.card_my == 1) {
                            val lstType = database.cardDao().getCouponType(card.id)
                            val lstDiscount = database.cardDao().getCouponDiscount(card.id)
                            var content = ""
                            for (i in 0..(lstType.size - 1)) {
                                content = content.plus((lstType[i]).plus(":"))
                                content = content.plus(lstDiscount[i]).plus("\n")
                            }
                            listCard.add(CardPrefab(card.card_img, card.card_name, content))
                        }
                    }
                    uiThread { myRecyclerView.adapter?.notifyDataSetChanged()
                        val textCardSize = myView.findViewById<TextView>(R.id.text_cardSize)
                        textCardSize.text = "(${listCard.size})"
                    }
                }
            }
    }

    class TagViewHolder(view: View):RecyclerView.ViewHolder(view){
        var tag = view.checkbox_tag
    }
}