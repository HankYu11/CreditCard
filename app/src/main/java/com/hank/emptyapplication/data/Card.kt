package com.hank.emptyapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card")
class Card(
    val card_name : String,
    val card_img : Int,
    @PrimaryKey
    var id: Int,
    var card_my : Int = 0
)

@Entity(tableName = "coupon")
class Coupon(
    val type : String,
    val discount : String,
    @PrimaryKey
    var id : Int
)

@Entity(tableName = "card_coupon")
class CardCoupon(
    val card_id: Int,
    val coupon_id : Int,
    @PrimaryKey
    var id : Int
)




