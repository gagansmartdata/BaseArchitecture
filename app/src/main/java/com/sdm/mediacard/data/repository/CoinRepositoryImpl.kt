package com.sdm.mediacard.data.repository

import com.sdm.mediacard.common.Resource
import com.sdm.mediacard.data.remote.CoinPaprikaApi
import com.sdm.mediacard.data.remote.dto.toCoin
import com.sdm.mediacard.data.remote.dto.toCoinDetail
import com.sdm.mediacard.domain.model.Coin
import com.sdm.mediacard.domain.model.CoinDetail
import com.sdm.mediacard.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi
) : CoinRepository {

    //Tip!!--use Mutex to save in-memory cache
    override fun getCoins(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading<List<Coin>>())
            val coins = api.getCoins().map {
                it.toCoin()
            }
            emit(Resource.Success<List<Coin>>(coins))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Coin>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Coin>>("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun getCoinById(coinId: String): CoinDetail {
        return api.getCoinById(coinId).toCoinDetail()
    }

}