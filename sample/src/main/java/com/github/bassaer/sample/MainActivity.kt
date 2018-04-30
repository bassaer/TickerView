package com.github.bassaer.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.github.bassaer.tickerview.TickerTextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layout = findViewById<FrameLayout>(R.id.frame_layout)
        val tickerView = TickerTextView(this)
        layout.addView(tickerView)

    }
}
