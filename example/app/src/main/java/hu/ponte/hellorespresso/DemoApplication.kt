package hu.ponte.hellorespresso

import android.app.Application
import hu.ponte.mobile.respresso.live_edit.Respresso

class DemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Respresso.init(this, BuildConfig.RespressoPreRelease)
    }
}