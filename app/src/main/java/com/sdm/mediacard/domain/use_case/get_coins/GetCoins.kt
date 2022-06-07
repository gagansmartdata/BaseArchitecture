package com.sdm.mediacard.domain.use_case.get_coins

import com.sdm.mediacard.common.Resource
import com.sdm.mediacard.domain.model.Coin
import com.sdm.mediacard.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
/**
 * getCoinById is better way of using a Use case, So ignore this one.
 */
class GetCoins @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<List<Coin>>> {
        return repository.getCoins()
    }
}