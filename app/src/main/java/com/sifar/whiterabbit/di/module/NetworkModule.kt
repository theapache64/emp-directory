package com.sifar.whiterabbit.di.module

import com.sifar.whiterabbit.data.remote.ApiInterface
import com.sifar.whiterabbit.util.calladapter.flow.FlowResourceCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by theapache64 : Feb 17 Wed,2021 @ 17:39
 */
@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://mocky.io/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(FlowResourceCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}