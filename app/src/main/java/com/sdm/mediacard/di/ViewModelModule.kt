package com.sdm.mediacard.di

import com.sdm.mediacard.data.remote.CoinPaprikaApi
import com.sdm.mediacard.data.repository.CoinRepositoryImpl
import com.sdm.mediacard.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class) //Can only be used in viewModels
object ViewModelModule {

    @ViewModelScoped //it destroy with viewModel
    @Provides
    fun providePaprikaApi(retrofit: Retrofit): CoinPaprikaApi {
        return retrofit
            .create(CoinPaprikaApi::class.java)
    }

    @ViewModelScoped
    @Provides
    fun provideCoinRepository(api: CoinPaprikaApi): CoinRepository {
        return CoinRepositoryImpl(api)
    }
}