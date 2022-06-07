package com.sdm.mediacard.presentation.feature_one

import androidx.lifecycle.viewModelScope
import com.sdm.mediacard.base.ViewModelG
import com.sdm.mediacard.base.UIState
import com.sdm.mediacard.common.Resource
import com.sdm.mediacard.domain.model.Coin
import com.sdm.mediacard.domain.repository.PreferencesHelper
import com.sdm.mediacard.domain.use_case.get_coins.GetCoins
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoins,
    private val protoPref: PreferencesHelper
) : ViewModelG<UIState<List<Coin>>>() {

    override val mutableState: MutableStateFlow<UIState<List<Coin>>> = MutableStateFlow(
            UIState<List<Coin>>()
    )

    init {
        viewModelScope.launch {
            protoPref.setUserId("Gagan")//example of using DataStore (new SharedPref replacement)
        }
        getCoins()
    }

    /**
     * fetch coins list using the use cases.
     */
    private fun getCoins() {
        getCoinsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    updateUIState {
                        it.copy(
                            data = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
                is Resource.Error -> {
                    updateUIState {
                        it.copy(
                                error = result.message ?: "An unexpected error occurred"
                                )
                    }
                }
                is Resource.Loading -> {
                    updateUIState {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

}