package com.sdm.mediacard.presentation.feature_two

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sdm.mediacard.R
import com.sdm.mediacard.base.BaseActivity
import com.sdm.mediacard.common.Constants
import com.sdm.mediacard.common.compose_ui.CircularLoading
import com.sdm.mediacard.databinding.ActivityCoinDetailsBinding
import com.sdm.mediacard.domain.model.CoinDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailsActivity : BaseActivity<ActivityCoinDetailsBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_coin_details
    override var binding: ActivityCoinDetailsBinding
        get() = setUpBinding()
        set(value) {}

    private val viewModel:CoinDetailViewModel by viewModels()

    override fun onCreate() {
        binding.composeView.apply {
            setContent {
                showCoinDetails()
            }
        }
    }


    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun showCoinDetails(){
        var showLoader by remember {mutableStateOf(true)}

                MaterialTheme {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        Alignment.Center
                    ) {
                        showLoader = viewModel.state().collectAsState().value.isLoading
                        CircularLoading(show = showLoader)
                        viewModel.state().collectAsState().value.data?.let { showCoinDetailsText(data = it) }
                    }
        }
    }

    @Composable
    fun showCoinDetailsText(data : CoinDetail){
        Column(horizontalAlignment =Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()) {
                Text(text = data.name, fontWeight = FontWeight.Bold, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                Text(text = "-->")
                Text(text = data.symbol,fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Text(text = data.description ?: "No Description " ,fontWeight = FontWeight.Light)
        }

    }

    companion object{
        fun start(context: Context,coinId :String){
            context.startActivity(Intent(context, CoinDetailsActivity::class.java).apply {
                putExtra(Constants.PARAM_COIN_ID,coinId)
            })
        }
    }
}

