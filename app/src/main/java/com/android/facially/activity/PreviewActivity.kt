package com.android.facially.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.opengl.GLES11Ext
import android.opengl.GLES30
import android.opengl.GLES31
import android.opengl.Matrix
import android.util.Log
import com.android.facially.OesRender
import com.android.facially.TAG
import com.android.facially.calculateMvp
import com.android.facially.opengl.GLFramebuffer
import com.android.facially.opengl.GLProgram
import com.android.facially.opengl.GLShader
import com.android.facially.opengl.GLVao
import com.android.facially.readAssert
import java.nio.ByteBuffer

open class PreviewActivity : CameraActivity() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, PreviewActivity::class.java))
        }
    }

    protected var oesRender: OesRender? = null

    override fun onSurfaceDestroy() {
        oesRender?.onDestroy()
    }

    var framebuffer: GLFramebuffer? = null
    open fun test(width: Int, height: Int) {
        val buffer = ByteBuffer.allocateDirect(width * height * 4);
        buffer.position(0)
        GLES30.glReadPixels(
            0,
            0,
            width,
            height,
            GLES30.GL_RGBA,
            GLES30.GL_UNSIGNED_BYTE,
            buffer
        )
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.copyPixelsFromBuffer(buffer)
        Log.e(TAG, "test: -------- check bitmap")
    }


    override fun onDraw(
        oes: Int,
        rotation: Int,
        displayRotation: Int,
        width: Int,
        height: Int,
        surfaceWidth: Int,
        surfaceHeight: Int,
    ) {
        if (oesRender == null)
            oesRender = OesRender(this)

        oesRender?.onDraw(
            oes,
            rotation,
            displayRotation,
            cameraMatrix,
            width,
            height,
            surfaceWidth,
            surfaceHeight,
            true
        )
    }
}