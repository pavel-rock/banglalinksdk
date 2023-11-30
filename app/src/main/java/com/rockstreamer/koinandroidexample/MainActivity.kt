package com.rockstreamer.koinandroidexample

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.rockstreamer.iscreensdk.listeners.oniScreenPremiumCallBack
import com.rockstreamer.iscreensdk.utils.cleanIScreenSDK
import com.rockstreamer.iscreensdk.utils.iScreenSDKInit
import com.rockstreamer.iscreensdk.utils.openDetailsScreen
import com.rockstreamer.iscreensdk.utils.openiScreenContentFromBl
import com.rockstreamer.iscreensdk.utils.openiScreenSDK
import com.rockstreamer.koinandroidexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , oniScreenPremiumCallBack {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        binding.fab.setOnClickListener { view ->
            //openiScreenSDK(this)

            openiScreenContentFromBl(id = "5479", type = "video", this)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPremiumContentClick(context: Context, contentId: String, type: Int) {
        //openDetailsScreen(id = contentId, type = type)
        //Log.d("APP_STATUS", "Premium Content")
    }

    override fun onTokenInvalid() {
        Log.d("APP_STATUS", "On Invalid Token asdalskjdalsdj")
        iScreenSDKInit(apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6Mjk2OTEwLCJwcm92aWRlcl9pZCI6ImRHdTd6Q1NIMVRFTHZRMVhKY2JXaU4iLCJyb2xlIjowLCJ1c2VybmFtZSI6Iis4ODAxODg0MDQzNjAzIiwicGxhdGZvcm0iOiJpc2NyZWVuIiwic3Vic2NyaWJlIjpmYWxzZSwicGFja2FnZUluZm8iOm51bGwsImlhdCI6MTcwMTMzMzE3NywiZXhwIjoxNzAxNDE5NTc3fQ.Unw3bemKhvOneyIQsO4LYzj1t6TvA0LmObDxIHVPQKTv951qES43jxDdLL7v2AN6nC8cOA5WeWuFh2foDAmi_9NIH7C3P7j5mitEpsXW1bSeh0xVtw61m53MLuYVX-P9w4H3AukA4LFTfBtq6sOow10kdNv24CtDlkIYZiQU7s1LmvH_p5YKnbTHR7cqkSoQ5PTmczGURVb98oCVu043RPVTUAaJERsczgBvXmD8kfyQVIUoemHzukbbimepAebGhSMO1Et5hUjQsS7w_CTXOMCii0qvc86aGROYnmJyBqAXPQOfmeC8qO5-_cRa2ICMZjTViwn7UvsEL1FOCP2WWg0taSQ_FJDugwyVO0iUQMfjD62h1UYxLWQJ3D_zZcYdr-JjSz-GzuEMgaG3Dp7i-3-HdQHUKoZ0XLp2o28zD8IS4qTvbfgfcKQDpTEs6W-kSJ-YfeuAtsKzpMle0LlHTuaM3Tsv8f2voLNXCswRPxvw0lZSh4y_th2jIp7pcbVSpvNzj3ogHS8iRSH1z8h0HesvvF_5J4y7KcJw9V0DvMziQK9STkFIhOo7B_M1j5X7fKGZ7gNieYUv77_H3-Pw9uvSz1tWPqrHAD8TL4jvfwmM8Ri80A3TnSxn3wLxydT4EaFt2Ea1opTBA40mPuf8lzvOvEFC76zupUtt5pgwTbA")
    }

}