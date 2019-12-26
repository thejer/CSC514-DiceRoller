package io.budge.diceroller

import android.content.Context
import android.opengl.GLSurfaceView
import android.os.CountDownTimer
import android.util.AttributeSet

/**
 * Created by burt on 2016. 6. 15..
 */
class OGLView : GLSurfaceView {
    var oglRenderer = OGLRenderer(context)

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init()
    }

    private fun init() { // use opengl es 2.0
        setEGLContextClientVersion(2)
        // store opengl context
        preserveEGLContextOnPause = true
        // set renderer
        setRenderer(oglRenderer)
    }

    fun spinGuys() {
        object : CountDownTimer(6000, 1) {
            override fun onTick(millisUntilFinished: Long) {
                oglRenderer.spin(50f)
                this@OGLView.isEnabled = false
            }

            override fun onFinish() {
                this@OGLView.isEnabled = true
            }
        }.start()
    }
}