package com.demo.shoppinglist.ui.ads

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

object AdsManager {

    private const val AD_UNIT_ID = "ca-app-pub-2194266417806448/6715379447"
    private const val AD_UNIT_ID_TEST = "ca-app-pub-3940256099942544/6300978111"

    fun createAds(context: Context, adView: AdView) {
        MobileAds.initialize(context)

        val adRequest = AdRequest.Builder().build()
//        adView.adUnitId = AD_UNIT_ID_TEST
        adView.loadAd(adRequest)
    }
}