package com.hank.emptyapplication


import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hank.emptyapplication.data.model.Card
import com.hank.emptyapplication.data.model.CardCoupon
import com.hank.emptyapplication.data.CardDatabase
import com.hank.emptyapplication.data.model.Coupon


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNav : BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,AllCardsFragment()).commit()
        AsyncTask.execute {
            initData()
        }
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

    fun initData(){
        val database = CardDatabase.getInstance(this)
        //card
        val card = Card("花旗", R.drawable.card, 0)
        val card1 = Card("玉山", R.drawable.card_yushan, 1)
        val card2 = Card("匯豐", R.drawable.card_huafon, 2)
        val card3 = Card("國泰", R.drawable.card_guotai, 3)
        database?.cardDao()?.insertCard(card)
        database?.cardDao()?.insertCard(card1)
        database?.cardDao()?.insertCard(card2)
        database?.cardDao()?.insertCard(card3)
        //coupon
        val coupon = Coupon("海外", "50%", 0)
        val coupon1 = Coupon("吃飯", "30%", 1)
        database?.cardDao()?.insertCoupon(coupon)
        database?.cardDao()?.insertCoupon(coupon1)
        //cardcoupon
        val cardCoupon = CardCoupon(card.id, coupon.id, 0)
        val cardCoupon1 = CardCoupon(card.id, coupon1.id, 1)
        val cardCoupon2 = CardCoupon(card1.id, coupon.id, 2)
        val cardCoupon3 = CardCoupon(card2.id, coupon1.id, 3)
        val cardCoupon4 = CardCoupon(card3.id, coupon.id, 4)
        database?.cardDao()?.insertCardCoupon(cardCoupon)
        database?.cardDao()?.insertCardCoupon(cardCoupon1)
        database?.cardDao()?.insertCardCoupon(cardCoupon2)
        database?.cardDao()?.insertCardCoupon(cardCoupon3)
        database?.cardDao()?.insertCardCoupon(cardCoupon4)
    }
}
