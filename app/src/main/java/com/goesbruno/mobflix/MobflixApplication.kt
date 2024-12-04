package com.goesbruno.mobflix

import android.app.Application
import com.goesbruno.mobflix.di.appModule
import com.goesbruno.mobflix.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MobflixApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MobflixApplication)
            modules(
                appModule,
                storageModule
            )
        }
    }


}