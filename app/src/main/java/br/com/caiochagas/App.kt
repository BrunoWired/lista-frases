package br.com.caiochagas

import android.app.Application
import android.arch.persistence.room.Room
import br.com.caiochagas.data.source.local.AppDatabase
import com.google.android.gms.ads.MobileAds

class App : Application() {

    companion object {
        lateinit var db: AppDatabase
    }

    override fun onCreate() {

        super.onCreate()

        db = Room.databaseBuilder(this, AppDatabase::class.java, "messages")
                .fallbackToDestructiveMigration()
                .build()

        MobileAds.initialize(this, getString(R.string.admob_app))
        MobileAds.setAppMuted(true)
        MobileAds.setAppVolume(0f)
    }
}