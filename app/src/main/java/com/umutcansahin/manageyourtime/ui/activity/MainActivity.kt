package com.umutcansahin.manageyourtime.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.umutcansahin.manageyourtime.R
import com.umutcansahin.manageyourtime.common.extensions.gone
import com.umutcansahin.manageyourtime.common.extensions.visible
import com.umutcansahin.manageyourtime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //ca-app-pub-5509612590921982~6834076783 --> app id
    //ca-app-pub-5509612590921982~6834076783 --> admob uygulama kimliği
    //ca-app-pub-5509612590921982/7846979397 --> admob reklam kimliği
    //ca-app-pub-3940256099942544/6300978111 --> test2
    //ca-app-pub-3940256099942544~3347511713 --> test1

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var navController: NavController
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
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.onBoardingFragment ->{
                    binding.adView.gone()}
                else -> {
                    binding.adView.visible()
                }
            }
        }
    }
}