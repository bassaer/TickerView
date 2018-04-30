package com.github.bassaer.tickerview

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.concurrent.ScheduledExecutorService


/**
 * Ticker Text View
 * Created by nakayama on 2018/04/30.
 */
class TickerTextView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private var surfaceHolder = holder
    private lateinit var scheduledExecutorService: ScheduledExecutorService
    private val speed = 1
    private var viewWidth = 0
    private var viewHeight = 0
    private var paint = Paint()
    private val text = "Hello World"
    private var textWidth = 0f
    private var wholeTextWidth = 0f
    private var textSize = context.resources.getDimensionPixelSize(R.dimen.text_size)
    private var textHeight = 0f
    private var currentPosition = 0f
    private var scrollTime = 10

    init {
        surfaceHolder = holder
        surfaceHolder.addCallback(this)
        paint = Paint()
    }

    fun draw(X: Float, Y: Float) {
        val canvas = surfaceHolder.lockCanvas()
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        canvas.drawText(text, X, Y, paint)

        surfaceHolder.unlockCanvasAndPost(canvas)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }


    override fun surfaceDestroyed(holder: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
        textWidth = paint.measureText(text)
        wholeTextWidth = viewWidth + textWidth
        textHeight = (viewHeight + getFontHeight(textSize.toFloat())) / 2 + paddingTop - paddingBottom + 2

    }

    private fun getFontHeight(fontSize: Float): Float {
        val paint = Paint()
        paint.textSize = fontSize
        val metrics = paint.fontMetrics
        return Math.ceil(metrics.descent.toDouble() - metrics.ascent.toDouble()).toFloat()
    }

    internal inner class TickerThread : Runnable {
        override fun run() {
            while (true) {
                draw(viewWidth - currentPosition, textHeight)
                currentPosition += speed
                if (currentPosition > wholeTextWidth) {
                    currentPosition = 0f
                    --scrollTime
                }
                if (scrollTime <= 0) {
                   break
                }
            }
        }
    }

}