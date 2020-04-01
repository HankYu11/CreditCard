package com.hank.emptyapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Card::class,MyCard::class,Coupon::class,CardCoupon::class),version = 1)
abstract class CardDatabase : RoomDatabase(){
    abstract fun cardDao() : CardDao
    companion object{
        private var instance : CardDatabase? = null
        fun getInstance(context : Context) : CardDatabase?{
            if(instance == null){
                instance = Room.databaseBuilder(context,CardDatabase::class.java,
                    "Card.db").build()
            }
            return instance
        }
    }
}