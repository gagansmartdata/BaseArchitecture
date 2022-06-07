package com.sdm.mediacard.presentation

import androidx.lifecycle.lifecycleScope
import com.sdm.mediacard.R
import com.sdm.mediacard.base.BaseActivity
import com.sdm.mediacard.databinding.ActivityMainBinding
import com.sdm.mediacard.presentation.feature_one.CoinListActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main
    override var binding: ActivityMainBinding
        get() = setUpBinding()
        set(value) {}


    override fun onCreate() {
        lifecycleScope.launchWhenStarted {
            delay(600)

            CoinListActivity.start(this@MainActivity)
        }
    }
}