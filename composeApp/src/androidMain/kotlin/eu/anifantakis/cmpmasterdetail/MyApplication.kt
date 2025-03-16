package eu.anifantakis.cmpmasterdetail

import android.app.Application
import eu.anifantakis.cmpmasterdetail.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@MyApplication)
        }
    }
}