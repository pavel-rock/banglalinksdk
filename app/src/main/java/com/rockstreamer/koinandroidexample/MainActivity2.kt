package com.rockstreamer.koinandroidexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.rockstreamer.iscreensdk.utils.stopIScreen

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var button = findViewById<Button>(R.id.close_button) as Button
        button.setOnClickListener {
            stopIScreen()
        }

    }
}