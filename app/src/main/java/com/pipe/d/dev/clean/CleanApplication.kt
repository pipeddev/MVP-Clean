package com.pipe.d.dev.clean

import android.app.Application
import com.pipe.d.dev.clean.common.mainModule
import org.koin.core.context.startKoin

class CleanApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(mainModule)
        }
    }
}