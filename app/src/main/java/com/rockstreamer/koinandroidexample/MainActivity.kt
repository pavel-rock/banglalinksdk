package com.rockstreamer.koinandroidexample

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.rockstreamer.iscreensdk.listeners.oniScreenPremiumCallBack
import com.rockstreamer.iscreensdk.utils.iScreenSDKInit
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
            openiScreenSDK(this)

            //openiScreenContentFromBl(id = "5479", type = "video", this)
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

    override fun onPremiumContentClick(context: Context, contentId: String, type: String) {
//        iScreenSDKInit(apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NDE0OSwicHJvdmlkZXJfaWQiOiJNbjdaNnpXN2QyWkpLZktlSGJnOFZFNnNSMXoyIiwicm9sZSI6MCwidXNlcm5hbWUiOiIrODgwMTc3ODQ3NDMzMyIsInBsYXRmb3JtIjoiaXNjcmVlbiIsInN1YnNjcmliZSI6dHJ1ZSwicGFja2FnZUluZm8iOnsibmFtZSI6Ik1vbnRobHlTdWJzICggRXhwaXJlIEF0IDIwMjQtMDEtMTAgKSIsImRlc2NyaXB0aW9uIjoiTW9udGhseVN1YnMgKCBFeHBpcmUgQXQgMjAyNC0wMS0xMCApIiwicHJpY2UiOiI4OSIsInVuaXQiOiJCRFQiLCJjb3VudHJ5Q29kZSI6IkJEIiwiY29sb3JDb2RlIjpudWxsLCJwcm9kdWN0SWQiOiJJU0NSRUVOX1NTTF9QTEFOXzEiLCJwYXltZW50TWV0aG9kVHlwZSI6InNzbGNvbW1lcnoiLCJleHBpcmVEYXRlIjoiMjAyNC0wMS0xMCJ9LCJpYXQiOjE3MDIyOTUyODUsImV4cCI6MTcwMjM4MTY4NX0.brj7dpqPRtAKcLen167z7kf5c8VtI6V0vNav-jpPzFfsSRVtx6JUVYgWvP1A7FZEy7zCb7xLQuRoV6WKh46GZole2BhV_Fjt4drPd4t7CqUJVfZY4NbeatLE6obHSHrJCkbrQ2hEcM34P2JEeeWMcwnLkdXeXpl6_V0XETZOv1_QMafIqrGf7Ha3ZBfC8_e_q7sGApWmYpc4lwdkVIDHgF5DbH395nDwYvpPRmRoZRUG4y14V6gbsfIYgXUkVbzO2SDeOb3FWN0I8eIx6aY1vk9kgFdwjp-VFSiiOGGEijqbEfLMdoSAjTegW7wD4pL0cUf3vTtJCsVDFF-kDy0Nida1bBi_2iNYV9AxUSH-gC7leNFw5rAinx9S3MypEOEVxAJXasTYyCg_l1t3IeFe-uJPIv4PxOVR6r7jz649NRajR5ZrTdUWcOsjNMvqQ_AvPvx3P9SFhYda3Ivpyt7a6T1c_LPvhdNThpOQuu_C6Sv_nA42jx5onmyCn2x3xvxcUEFsLXW7lJxrnyontZKu9pyafe7BwPCHy_NhR8my3rMecoSxjD0PwxBLJhYBZEa2mbK34uqZGK0WjdW1ddCZTDqv1mLxYazN7HGJue92N_Wwh-cArlKH7Vup33-negg4NyytdBkZCFUOoiS9sJxQLdWjNy8AMD7i_FME2QDZnyg")
//        openDetailsScreen(id = contentId, type = type)
//        Log.d("TOKEN", "type = ${type}")

        (context as Activity).finish()
    }

    override fun onTokenInvalid() {
        iScreenSDKInit(apiToken = "NEW TOKEN FROM BACKGROUND")
    }

}