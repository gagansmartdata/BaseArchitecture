package com.sdm.mediacard.presentation.feature_two

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sdm.mediacard.base.ViewModelG
import com.sdm.mediacard.base.UIState
import com.sdm.mediacard.common.Constants
import com.sdm.mediacard.common.Resource
import com.sdm.mediacard.domain.model.CoinDetail
import com.sdm.mediacard.domain.repository.PreferencesHelper
import com.sdm.mediacard.domain.use_case.get_coin.GetCoinById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinById,
    private val prefData: PreferencesHelper,
    savedStateHandle: SavedStateHandle
) : ViewModelG<UIState<CoinDetail>>() {

    override val mutableState: MutableStateFlow<UIState<CoinDetail>> = MutableStateFlow(
        UIState<CoinDetail>()//set initial state
    )

    init {//you can get intent's put extra from here.
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
            getCoin(coinId)
        }

        viewModelScope.launch {
        prefData.getUserId().collect {
            Log.e("Test: ",it )
         }
        }
    }

    /**
     * fetch coin details using the use case.
     */
    private fun getCoin(coinId: String) {
        getCoinUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    updateUIState {
                        it.copy(data = result.data,
                        isLoading = false)
                    }
                }
                is Resource.Error -> {
                    updateUIState {
                        it.copy(error = result.message ?: "An unexpected error occurred",
                            isLoading = false)
                    }
                }
                is Resource.Loading -> {
                    updateUIState {
                        it.copy(isLoading = true)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}