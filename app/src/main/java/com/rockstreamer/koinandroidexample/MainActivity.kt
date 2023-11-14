package com.rockstreamer.koinandroidexample

import android.content.Context
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.rockstreamer.iscreensdk.listeners.oniScreenPremiumCallBack
import com.rockstreamer.iscreensdk.utils.openiScreenSDK
import com.rockstreamer.koinandroidexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , oniScreenPremiumCallBack {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        binding.fab.setOnClickListener { view ->
            openiScreenSDK(apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6Mjc1Njk1LCJwcm92aWRlcl9pZCI6IjcyMzg0NzI5MzQ3Mjk4NDM3Iiwicm9sZSI6MCwidXNlcm5hbWUiOiJhaXNsYW0yMDYyMDZAZ21haWwuY29tIiwicGxhdGZvcm0iOiJpc2NyZWVuIiwic3Vic2NyaWJlIjpmYWxzZSwicGFja2FnZUluZm8iOm51bGwsImlhdCI6MTY5ODY0OTYyMCwiZXhwIjoxNjk4NzM2MDIwfQ.JZfBzuvNf2SuX0PxJiJ3tnML3ZIFmlv4Tw_4iqtf6jD1gm2g1iLbyG6Z_1UhqGQIW8S-dyHnqMC_qeHjCQyH5ZPBevA4mvVtqlFm_GiOmpJlMAwFEtnStWMio8fmBnUoB_ea_g2IBLoqjBFVcSC82eFNSCxQobxIfieHI_0fm0Bm3ixJn2pTQoRQEBCPzlBpbXxCL_xPVuXMdxrc3fMHAV9QXStcj-wFsOrWfFs29OJS16wKMN0CRhh02Q8ByLPIuLPfjzOKfhWolKW93XZEoa181CpG8qcYAiJ6sCpkO8Y41v3TaBPqbi1qYQC9KwSVwqpDJTuLW5j0RKhnIQFNZE76wS32XpPwpjzo_0UrrFheeq7RL1P_Pm9SQ8WBsw6qrXt0Sn_H_5jNRt-l08pyBmUsVMPYnMiwJ5q2oKUNTZnCznO-gyFjQZrKTQwGBPGhjKAI0wce3mPHvc-Y9TyyIbnpH6m4pdvQCfgxXGMYPjJeQAMpWezyAyjPWRMg7AwClQB5MdMNO2HewUhqHuieoGVrezt-7Hq-RfmOMt0Vyik4KEOZv0toEfqIrmGIrV2Mxqmni37hqoKVq_QGQ2FaBuLxf59Y2i6UTsBhF1p2bIct4QLUEv8tCRbncucKRR4PaDwoeDZW5z8F2gxvxgNRF3LgcDG7L7ZRq0T7bv93U-4", this)
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

    }

    override fun onTokenInvalid() {

    }

}