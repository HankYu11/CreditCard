package com.hank.emptyapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(card : Card)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMyCard(myCard: MyCard)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoupon(coupon: Coupon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCardCoupon(cardCoupon: CardCoupon)

    @Query("select * from Coupon where id == :coupon_id")
    fun getCoupon(coupon_id : Int) : Coupon

    @Query("select * from mycard")
    fun getAllMyCard() : List<MyCard>

    @Query("select * from card")
    fun getAllCards() : List<Card>

    @Query("select coupon_id from CardCoupon where card_id = :card_id")
    fun getCouponID(card_id : String) : List<Int>

}