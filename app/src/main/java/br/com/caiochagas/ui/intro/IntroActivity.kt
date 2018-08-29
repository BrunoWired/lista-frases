package br.com.caiochagas.ui.intro

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import br.com.caiochagas.R
import br.com.caiochagas.ui.day.DayActivity
import br.com.caiochagas.ui.main.MainActivity
import br.com.caiochagas.utils.Constants.MESSAGE_DAY
import br.com.caiochagas.utils.NotificationHelper
import br.com.caiochagas.utils.ext.hideSystemUi
import br.com.caiochagas.utils.ext.launch
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd

class IntroActivity : AppCompatActivity() {

    private val isMessageOfDay by lazy { intent.getBooleanExtra(MESSAGE_DAY, false) }

    private val interstitialAd by lazy { InterstitialAd(this) }

    private var nextActivity = true

    private var showInterstitialAd = false

    private var interstitialAdOpened = false

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_intro)

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)

        NotificationHelper.createNotificationChannel(this)

        NotificationHelper.reschedule(this)

        loadInterstitialAd()

        handler.postDelayed({ showInterstitialAd = true }, 1500)

        handler.postDelayed({ showInterstitialAd() }, 2000)

        handler.postDelayed({ nextActivity() }, 10000)
    }

    override fun onStart() {
        super.onStart()
        hideSystemUi()
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }

    private fun loadInterstitialAd() {

        interstitialAd.adUnitId = getString(R.string.admob_interstitial)

        interstitialAd.adListener = object : AdListener() {

            override fun onAdOpened() {
                interstitialAdOpened = true
            }

            override fun onAdClosed() {
                interstitialAdOpened = false
                nextActivity()
            }

            override fun onAdLoaded() = showInterstitialAd()

            override fun onAdFailedToLoad(error: Int) = nextActivity()
        }

        interstitialAd.loadAd(AdRequest.Builder().build())
    }

    private fun showInterstitialAd() {

        if (showInterstitialAd && interstitialAd.isLoaded) {
            showInterstitialAd = false
            interstitialAd.show()
        }
    }

    private fun nextActivity() {

        if (interstitialAdOpened || !nextActivity) return

        showInterstitialAd = false

        nextActivity = false

        if (isMessageOfDay) launch<DayActivity>() else launch<MainActivity>()

        finish()
    }
}
