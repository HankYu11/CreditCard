package com.hank.emptyapplication

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.room.Database
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hank.emptyapplication.data.*

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNav : BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,AllCardsFragment()).commit()
        val database = CardDatabase.getInstance(this)
        val fuban = Card("FuBan")
        val yushan = Card("YuShan")
        val myCard = MyCard("FuBan")
        val coupon1 = Coupon("海外","8%",1)
        val coupon2 = Coupon("國內","9%",2)
        val cardCoupon1 = CardCoupon(fuban.card_name,coupon1.id)
        val cardCoupon2 = CardCoupon(yushan.card_name,coupon2.id)
        Thread{
            database?.cardDao()?.insertCard(fuban)
            database?.cardDao()?.insertCard(yushan)
            database?.cardDao()?.insertMyCard(myCard)
            database?.cardDao()?.insertCoupon(coupon1)
            database?.cardDao()?.insertCoupon(coupon2)
            database?.cardDao()?.insertCardCoupon(cardCoupon1)
            database?.cardDao()?.insertCardCoupon(cardCoupon2)

            val cou = database?.cardDao()?.getCoupon(coupon1.id)
                Log.d(TAG, "coupon: ${cou?.type}${cou?.discount}")
                Log.d(TAG, "應該是海外8%")
            database?.cardDao()?.getCouponID(fuban.card_name)?.forEach{
                val tempCoupon = database?.cardDao()?.getCoupon(it)
                Log.d(TAG, "富邦的discount : ${tempCoupon.discount} ")
            }

            database?.cardDao()?.getAllCards()?.forEach {
                Log.d(TAG, "All Cards: ${it.card_name} ")
            }
            database?.cardDao()?.getAllMyCard()?.forEach{
                Log.d(TAG, "My Card: ${it.card_name} ")
            }
        }.start()
    }

    private val navListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            var fragment : Fragment? = null
            when(it.itemId){
                R.id.nav_all -> fragment = AllCardsFragment()
                R.id.nav_else -> fragment = ElseFragment()
                R.id.nav_my -> fragment = MyCardsFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment!!).commit()
            true
        }

}
