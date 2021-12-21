package cz.crusty.pokemon.base

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import cz.crusty.pokemon.BuildConfig
import cz.crusty.pokemon.inject.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(appModules)
        }

        Fresco.initialize(this)

        Timber.d("App Started !!!")
    }

}