package com.hank.emptyapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hank.emptyapplication.data.model.Card
import com.hank.emptyapplication.data.model.CardCoupon
import com.hank.emptyapplication.data.model.Coupon

@Dao
interface CardDao{

    @Insert
    fun insertAllCards(cards : List<Card>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(card : Card)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoupon(coupon: Coupon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCardCoupon(cardCoupon: CardCoupon)

    @Query("Update CARD set card_my = 1 where id == :card_id")
    fun setCardMy(card_id: Int)

    @Query("select type from card_coupon Inner join coupon on coupon_id == coupon.id where card_coupon.card_id = :card_id")
    fun getCouponType(card_id : Int) : List<String>

    @Query("select discount from card_coupon Inner join coupon on coupon_id == coupon.id where card_coupon.card_id = :card_id")
    fun getCouponDiscount(card_id : Int) : List<String>

    @Query("select * from card")
    fun getAllCards() : List<Card>

    @Query("select id from COUPON")
    fun getCouponID() : List<Int>

    @Query("select coupon_id from CARD_COUPON")
    fun getCardCouponCID() : List<Int>

}