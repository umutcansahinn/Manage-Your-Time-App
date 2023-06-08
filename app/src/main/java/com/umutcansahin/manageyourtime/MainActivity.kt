package com.umutcansahin.manageyourtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.umutcansahin.manageyourtime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //ca-app-pub-5509612590921982~6834076783 --> app id
    //ca-app-pub-5509612590921982~6834076783 --> admob uygulama kimliği
    //ca-app-pub-5509612590921982/7846979397 --> admob reklam kimliği
    //ca-app-pub-3940256099942544/6300978111 --> test2
    //ca-app-pub-3940256099942544~3347511713 --> test1

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }
}