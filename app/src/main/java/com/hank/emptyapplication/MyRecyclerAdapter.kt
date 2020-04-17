package com.hank.emptyapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hank.emptyapplication.data.model.Card

class MyRecyclerViewAdapter(val my_context : Context, val my_data : List<CardPrefab>) : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>(){
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