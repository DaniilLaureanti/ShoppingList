package com.demo.shoppinglist.ui.ads

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

const val AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"
private var TAG = "ShoppingListRewardedAd"

private var mIsLoading = false
private var mRewardedAd: RewardedAd? = null

object ShoppingListRewardedAd {

    fun loadRewardedAd(activity: Activity) {
        if (mRewardedAd == null) {
            mIsLoading = true
            var adRequest = AdRequest.Builder().build()

            RewardedAd.load(
                activity,
                AD_UNIT_ID,
                adRequest as AdManagerAdRequest,
                object : RewardedAdLoadCallback() {

                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        adError.message.let { Log.d(TAG, it) }
                        mIsLoading = false
                        mRewardedAd = null
                    }

                    override fun onAdLoaded(rewardedAd: RewardedAd) {
                        Log.d(TAG, "Ad was loaded.")
                        mRewardedAd = rewardedAd
                        mIsLoading = false
                    }
                }
            )
        }

        if (mRewardedAd != null) {
            mRewardedAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        mRewardedAd = null
                    }
                }
        }

        mRewardedAd?.show(
            activity
        ) {
            fun onUserEarnedReward(rewardItem: RewardItem) {
                Log.d("TAG", "User earned the reward.")
            }
        }
    }
}