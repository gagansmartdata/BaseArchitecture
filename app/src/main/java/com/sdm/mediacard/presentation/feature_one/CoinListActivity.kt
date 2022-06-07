package com.sdm.mediacard.presentation.feature_one

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sdm.mediacard.base.BaseActivity
import com.sdm.mediacard.common.compose_ui.CircularLoading
import com.sdm.mediacard.databinding.ActivityCoinListBinding
import com.sdm.mediacard.domain.model.Coin
import com.sdm.mediacard.presentation.feature_two.CoinDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import com.sdm.mediacard.R

@AndroidEntryPoint
class CoinListActivity : BaseActivity<ActivityCoinListBinding>()
{
    override val layoutId: Int
        get() = R.layout.activity_coin_list
    override var binding: ActivityCoinListBinding
        get() = setUpBinding()
        set(value) {}

    //using lazy loading to load the view model instance
    private val viewModel:CoinListViewModel by viewModels()

    override fun onCreate() {

        //You can use jetpack compose or xml for the layouts.
        //**Complex layouts are easier to achieve in xml**

        binding.composeView.apply {
            setContent {
                var showLoader by remember { mutableStateOf(true) }
                    Surface(color = MaterialTheme.colors.background) {
                        CircularLoading(show = showLoader)
                        showLoader = viewModel.state().collectAsState().value.isLoading
                        viewModel.state().collectAsState().value.data?.let { CoinList(it.take(20)) }
                    }
            }
        }
    }


    @Composable
    fun CoinList(dogs: List<Coin>) {
        LazyColumn {
            items(dogs) { dog ->
                CoinCard(dog)
            }
        }
    }

    @Composable
    fun CoinCard(dog: Coin) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable { CoinDetailsActivity.start(this, dog.id) },
            ) {
            Text(
             text = dog.name
            ,Modifier.padding(10.dp)
            )
        }
    }

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context, CoinListActivity::class.java))
        }
    }
}