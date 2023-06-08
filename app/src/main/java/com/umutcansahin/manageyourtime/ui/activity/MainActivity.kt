package com.umutcansahin.manageyourtime.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.gms.ads.*
import com.umutcansahin.manageyourtime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //ca-app-pub-5509612590921982~6834076783 --> app id
    //ca-app-pub-5509612590921982~6834076783 --> admob uygulama kimliği
    //ca-app-pub-5509612590921982/7846979397 --> admob reklam kimliği
    //ca-app-pub-3940256099942544/6300978111 --> test2
    //ca-app-pub-3940256099942544~3347511713 --> test1

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        mainViewModel.splash()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainViewModel.isLoading.value
            }
        }
        setContentView(binding.root)

        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }
}