package com.rockstreamer.iscreensdk.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.rockstreamer.iscreensdk.IScreenActivity
import com.rockstreamer.iscreensdk.R
import com.rockstreamer.iscreensdk.api.viewmodel.seemore.SeeMoreViewModel
import com.rockstreamer.iscreensdk.adapter.SeeMoreVideoPagingAdapter
import com.rockstreamer.iscreensdk.databinding.ActivitySeemoreBinding
import com.rockstreamer.iscreensdk.listeners.OnSeeMoreContentListener
import com.rockstreamer.iscreensdk.listeners.oniScreenPremiumCallBack
import com.rockstreamer.iscreensdk.utils.EXTRA_SEEMORE_ORIENTATION
import com.rockstreamer.iscreensdk.utils.EXTRA_SEEMORE_TITLE
import com.rockstreamer.iscreensdk.utils.EXTRA_SEE_MORE_ID
import com.rockstreamer.iscreensdk.utils.EqualSpacingItemDecoration
import com.rockstreamer.iscreensdk.utils.IMAGE_HORIZONTAL
import com.rockstreamer.iscreensdk.utils.IMAGE_VERTICAL
import com.rockstreamer.iscreensdk.utils.ITEM_SPACE
import com.rockstreamer.iscreensdk.utils.SERIES_CONTENT
import com.rockstreamer.iscreensdk.utils.VIDEO_CONTENT
import com.rockstreamer.iscreensdk.utils.getSubscriptionInformation
import com.rockstreamer.iscreensdk.utils.gone
import com.rockstreamer.iscreensdk.utils.openDetailsScreen
import com.rockstreamer.iscreensdk.utils.show
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SeeMoreActivity : AppCompatActivity(), OnSeeMoreContentListener {


    private val seeMoreViewModel : SeeMoreViewModel by inject()
    lateinit var seeMoreVideoAdapter: SeeMoreVideoPagingAdapter

    lateinit var binding: ActivitySeemoreBinding


    companion object{
        var context: Context ?= null
        private var callback: oniScreenPremiumCallBack?=null
        fun setInterfaceInstance(context: Context?){
            callback = context as oniScreenPremiumCallBack
        }
        fun stopiScreen(){
            if (context!=null){
                (context as Activity).finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.iscreen_toolbar_color)
        binding = ActivitySeemoreBinding.inflate(layoutInflater)
        context = this
        setContentView(binding.root)

        var title = intent.getStringExtra(EXTRA_SEEMORE_TITLE)
        var orientaion = intent.getStringExtra(EXTRA_SEEMORE_ORIENTATION)
        var seemoreId = intent.getStringExtra(EXTRA_SEE_MORE_ID)

        binding.idToolbar.toolbarBack.setOnClickListener {
            finish()
        }

        binding.idToolbar.toolbarTitle.text = title
        seeMoreVideoAdapter = SeeMoreVideoPagingAdapter(this, imageType = orientaion!!)
        when(orientaion){
            IMAGE_VERTICAL ->{
                binding.seemoreRecycleview.layoutManager = GridLayoutManager(this , 3)
            }
            IMAGE_HORIZONTAL ->{
                binding.seemoreRecycleview.layoutManager = GridLayoutManager(this, 2)
            }
        }
        binding.seemoreRecycleview.adapter = seeMoreVideoAdapter
        binding.seemoreRecycleview.addItemDecoration(
            EqualSpacingItemDecoration(
                ITEM_SPACE,
                EqualSpacingItemDecoration.GRID
            )
        )

        if (seemoreId != null) {
            Log.d("APP_STATUS", "Comes here")
            lifecycleScope.launch {
                seeMoreViewModel.getSeeMore(seemoreId).observe(this@SeeMoreActivity){
                    it?.let {
                        seeMoreVideoAdapter.submitData(lifecycle, it)
                    }
                }
            }
        }

        seeMoreVideoAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.progressbar.show()
            }
            else {
                binding.progressbar.gone()
            }
        }
    }

    override fun onSeeMoreContentClick(id: String, type: String, premium: Boolean) {
        if (premium){
            if (getSubscriptionInformation().subscribe){
                openDetailsScreen(id = id , type = type)
            }else{
                IScreenActivity.callback?.onPremiumContentClick(context = this, contentId = "$id", type = type)
            }
        }else{
            openDetailsScreen(id = id , type = type)
        }
    }
}