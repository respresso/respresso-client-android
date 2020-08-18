package hu.ponte.hellorespresso

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import hu.ponte.mobile.respresso.live_edit.Respresso

open class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val context = Respresso.wrapContext(newBase)
        super.attachBaseContext(context)
    }

    override fun onResume() {
        super.onResume()
        Respresso.create(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Respresso.destroy(this)
    }
}