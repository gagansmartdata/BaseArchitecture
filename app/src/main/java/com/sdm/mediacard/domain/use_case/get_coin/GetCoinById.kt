package com.sdm.mediacard.domain.use_case.get_coin

import com.sdm.mediacard.common.Resource
import com.sdm.mediacard.domain.model.CoinDetail
import com.sdm.mediacard.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.sdm.mediacard.R

class GetCoinById @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = 
    flow {
        try {
            emit(Resource.Loading<CoinDetail>())
            val coins = repository.getCoinById(coinId)
            emit(Resource.Success<CoinDetail>(coins))
        } catch(e: HttpException) {
            emit(Resource.Error<CoinDetail>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error<CoinDetail>(R.string.network_error))
        }
    }
}