package com.hank.emptyapplication.data.model

import com.hank.emptyapplication.R

class CardsData{
    companion object{
        fun prepopulateCardsData() : List<Card>{
            return listOf<Card>(
                Card("玉山", R.drawable.card_yushan, 0),
                Card("富邦", R.drawable.card, 1),
                Card("國泰", R.drawable.card_guotai, 2),
                Card("匯豐", R.drawable.card_huafon, 0)
                )
        }

    }
}