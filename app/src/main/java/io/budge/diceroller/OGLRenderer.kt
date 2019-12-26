package io.budge.diceroller

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.renderscript.Float3
import android.renderscript.Matrix4f
import io.budge.diceroller.glkit.ShaderProgram
import io.budge.diceroller.glkit.ShaderUtils
import io.budge.diceroller.glkit.TextureUtils
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by burt on 2016. 6. 15..
 */
class OGLRenderer(private val context: Context) : GLSurfaceView.Renderer {
    private var cube: Cube? = null
    private var lastTimeMillis = 0L
    override fun onSurfaceCreated(
        gl10: GL10,
        eglConfig: EGLConfig
    ) {
        val shader = ShaderProgram(
            ShaderUtils.readShaderFileFromRawResource(context, R.raw.simple_vertex_shader),
            ShaderUtils.readShaderFileFromRawResource(context, R.raw.simple_fragment_shader)
        )
        val textureName = TextureUtils.loadTexture(context, R.drawable.dice)
        cube = Cube(shader)
        cube!!.setPosition(Float3(0.0f, 0.0f, 0.0f))
        cube!!.setTexture(textureName)
        lastTimeMillis = System.currentTimeMillis()
    }

    override fun onSurfaceChanged(gl10: GL10, w: Int, h: Int) {
        GLES20.glViewport(0, 0, w, h)
        val perspective = Matrix4f()
        perspective.loadPerspective(85.0f, w.toFloat() / h.toFloat(), 1.0f, -150.0f)
        if (cube != null) {
            cube!!.setProjection(perspective)
        }
    }

    /**
     * GLSurfaceView has default 16bit depth buffer
     */
    override fun onDrawFrame(gl10: GL10) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        GLES20.glEnable(GLES20.GL_CULL_FACE)
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)
        val currentTimeMillis = System.currentTimeMillis()
        updateWithDelta(currentTimeMillis - lastTimeMillis)
        lastTimeMillis = currentTimeMillis
    }

    fun updateWithDelta(dt: Long) {
        val camera2 = Matrix4f()
        camera2.translate(0.0f, 0.0f, -5.0f)
        cube!!.setCamera(camera2)
        //        cube.setRotationY((float)( cube.rotationY + Math.PI * dt / (ONE_SEC * 0.1f) ));
//        cube.setRotationZ((float)( cube.rotationZ + Math.PI * dt / (ONE_SEC * 0.1f) ));
//        cube.setRotationX((float)( cube.rotationX + Math.PI * dt / (ONE_SEC * 0.1f) ));
        cube!!.draw(dt)
    }

    fun spin(second: Float) {
        val currentTimeMillis = System.currentTimeMillis()
        val dt = currentTimeMillis - lastTimeMillis
        cube!!.setRotationY((cube!!.rotationY + Math.PI * dt / (second * 0.1f)).toFloat())
        cube!!.setRotationZ((cube!!.rotationZ + Math.PI * dt / (second * 0.1f)).toFloat())
        cube!!.setRotationX((cube!!.rotationX + Math.PI * dt / (second * 0.1f)).toFloat())
        cube!!.draw(dt)
        lastTimeMillis = currentTimeMillis
    }

    companion object {
        private const val ONE_SEC = 50.0f // 1 second
    }

}