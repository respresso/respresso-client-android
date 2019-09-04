package hu.ponte.hellorespresso

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Bundle
import hu.ponte.mobile.respresso.live_edit.Respresso
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MotionEvent
import android.animation.ValueAnimator
import android.util.Log


class MainActivity : BaseActivity() {

    private var animator: ValueAnimator? = null
    private var borderLeftTopPadding = -1
    set(value) { if (field == -1) field = value; }
    private var shapeRightPadding = -1
        set(value) { if (field == -1) field = value; }
    private var shapeBottomPadding = -1
        set(value) { if (field == -1) field = value; }

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

        button_check.setOnClickListener {
            startActivity(webIntent)
        }

        button_check.setOnTouchListener { v, event ->
            initAnim()
            when (event.action) {
                MotionEvent.ACTION_DOWN -> pressAnim()
                MotionEvent.ACTION_UP -> releaseAnim()
            }
            false
        }
    }

    private fun initAnim() {
        val layer = button_check.background as? LayerDrawable
        val shape = layer?.getDrawable(0)
        val border = layer?.getDrawable(1)

        val boundsShape = shape?.bounds
        val boundsBorder = border?.bounds

        if (boundsBorder == null || boundsShape == null) return

        borderLeftTopPadding = boundsBorder.left
        shapeRightPadding = boundsShape.right
        shapeBottomPadding = boundsShape.bottom

        animator = ObjectAnimator.ofInt(0, borderLeftTopPadding)
        animator?.duration = 300

        animator?.addUpdateListener {
            val value = it.animatedValue as Int
            border.setBounds(borderLeftTopPadding - value, borderLeftTopPadding - value, boundsBorder.right, boundsBorder.bottom)
            shape.setBounds(boundsShape.left, boundsShape.top, shapeRightPadding + value, shapeBottomPadding + value)
        }
    }

    private fun pressAnim() {
        if (animator == null) initAnim()
        animator?.start()
    }

    private fun releaseAnim() {
        animator?.reverse()
    }

    override fun onStop() {
        super.onStop()
        animator?.removeAllUpdateListeners()
    }
}
