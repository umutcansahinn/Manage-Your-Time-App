package com.umutcansahin.manageyourtime.ui.home_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.umutcansahin.manageyourtime.base.BaseFragment
import com.umutcansahin.manageyourtime.common.NavType
import com.umutcansahin.manageyourtime.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private var mInterstitialAd: InterstitialAd? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        showAdd()
    }

    private fun showAdd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireActivity(),"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {

                mInterstitialAd = interstitialAd
            }
        })
    }

    private fun initView() {
        with(binding) {
            addScreen.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToAddFragment(
                        navType = NavType.ADD_NEW_ITEM,
                        data = null
                    )
                )
            }
            allListScreen.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToAllPlanFragment(
                        filter = null
                    )
                )
            }
            countDownTimerScreen.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToCountDownTimerFragment()
                )
            }
            stopWatchScreen.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToStopWatchFragment()
                )
            }
        }
    }
    override fun onStart() {
        super.onStart()
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(requireActivity())
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }
}