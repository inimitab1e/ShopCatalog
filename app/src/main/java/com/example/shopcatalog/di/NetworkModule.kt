package com.example.shopcatalog.di

import android.content.Context
import com.example.shopcatalog.data.network.AuthService
import com.example.shopcatalog.domain.security.PrefHelper
import com.example.shopcatalog.domain.utils.StringConstants
import com.example.shopcatalog.domain.utils.network_utils.interceptor.InterceptorTypeSelector
import com.example.shopcatalog.domain.utils.network_utils.interceptor.InterceptorType
import com.example.shopcatalog.domain.utils.network_utils.interceptor.activeForType
import com.example.shopcatalog.domain.utils.network_utils.interceptor.createAuthorizationInterceptor
import com.example.shopcatalog.domain.utils.network_utils.interceptor.getChuckerInterceptor
import com.example.shopcatalog.domain.utils.network_utils.retrofit.ResultAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val interceptorTypeSelector = InterceptorTypeSelector()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        prefHelper: PrefHelper
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(
                getChuckerInterceptor(context).activeForType(
                    InterceptorType.APPLICATION,
                    interceptorTypeSelector
                )
            )
            .addNetworkInterceptor(
                getChuckerInterceptor(context).activeForType(
                    InterceptorType.NETWORK,
                    interceptorTypeSelector
                )
            )
            .addInterceptor(createAuthorizationInterceptor(prefHelper))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(mOkHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(StringConstants.baseUrl)
            .client(mOkHttpClient)
            .addCallAdapterFactory(ResultAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(client: Retrofit): AuthService {
        return client.create(AuthService::class.java)
    }
}