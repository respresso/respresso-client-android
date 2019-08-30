package hu.ponte.hellorespresso

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import hu.ponte.mobile.respresso.live_edit.Respresso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        initRespresso()
    }

    private fun initRespresso() {
        Respresso.with(this).text(R.string.demo_abstract).withAutoUpdate().into(text_abstract)
        Respresso.with(this).text(R.string.demo_check_it_now).withAutoUpdate().into(button_check)
    }

    private fun init() {
        val url = "https://respresso.io/"
        val webIntent = Intent(Intent.ACTION_VIEW)
        webIntent.data = Uri.parse(url)

        button_check.setOnClickListener { startActivity(webIntent) }
    }
}
