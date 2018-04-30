package com.github.bassaer.tickerview

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.experimental.launch


/**
 * Ticker Text View
 * Created by nakayama on 2018/04/30.
 */
class TickerTextView(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs), SurfaceHolder.Callback {

    private var surfaceHolder = holder
    private val speed = 1
    private var viewWidth = 0
    private var viewHeight = 0
    private var paint = Paint()
    private val text = "Hello World"
    private var textWidth = 0f
    private var wholeTextWidth = 0f
    private var textSize = 0
    private var textHeight = 0f
    private var currentPosition = 0f
    private var scrollTime = 10
    private var density = 1f

    fun draw(x: Float, y: Float) {
        val canvas = surfaceHolder.lockCanvas()
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        canvas.drawText(text, x, y, paint)
        surfaceHolder.unlockCanvasAndPost(canvas)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }


    override fun surfaceDestroyed(holder: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        launch {
            Log.d(javaClass.name, "launch start whole : " + wholeTextWidth)
            while (true) {
                Log.d(javaClass.name, "start curPos: " + currentPosition)
                draw(viewWidth - currentPosition, textHeight)
                currentPosition += speed
                if (currentPosition > wholeTextWidth) {
                    Log.d(javaClass.name, "scrolling")
                    currentPosition = 0f
                    --scrollTime
                }
                if (scrollTime <= 0) {
                    Log.d(javaClass.name, "breaking")
                    break
                }
                Log.d(javaClass.name, "end")
            }
            Log.d(javaClass.name, "launch end curPos: " + currentPosition)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
        textWidth = paint.measureText(text)
        wholeTextWidth = viewWidth + textWidth
        textHeight = (viewHeight + getFontHeight(textSize.toFloat()/ density)) / 2 + paddingTop - paddingBottom + 2

    }

    private fun getFontHeight(fontSize: Float): Float {
        val paint = Paint()
        paint.textSize = fontSize
        val metrics = paint.fontMetrics
        return Math.ceil(metrics.descent.toDouble() - metrics.ascent.toDouble()).toFloat()
    }


    init {
        surfaceHolder = holder
        surfaceHolder.addCallback(this)
        paint = Paint()
        paint.color = Color.BLACK
        textSize = context.resources.getDimensionPixelSize(R.dimen.text_size)
        paint.textSize = textSize.toFloat()
        setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSLUCENT)
        val metric = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(metric)
        density = metric.density
        isFocusable = true
    }

}