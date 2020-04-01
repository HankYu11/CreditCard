package com.hank.emptyapplication.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Card(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val card_name : String)

@Entity
class MyCard (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val card_name : String)

@Entity
class Coupon(
    val type : String,
    val discount : String,
    @PrimaryKey
    val id : Int)

@Entity(primaryKeys = ["card_id","coupon_id"])
class CardCoupon(
    @ColumnInfo(name = "card_id")
    val card_id: String,
    @ColumnInfo(name = "coupon_id")
    val coupon_id : Int)




