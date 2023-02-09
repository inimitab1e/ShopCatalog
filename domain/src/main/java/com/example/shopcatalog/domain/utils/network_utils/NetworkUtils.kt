package com.example.student_tasks.network.utils

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.shopcatalog.domain.security.PrefHelper
import com.example.shopcatalog.domain.utils.StringConstants
import okhttp3.Interceptor

private fun collector(context: Context) = ChuckerCollector(
    context = context,
    showNotification = true,
    retentionPeriod = RetentionManager.Period.ONE_HOUR
)

@Suppress("MagicNumber")
fun getChuckerInterceptor(context: Context) = ChuckerInterceptor.Builder(context)
    .collector(collector(context))
    .maxContentLength(250_000L)
    .redactHeaders(emptySet())
    .alwaysReadResponseBody(false)
    .build()


enum class InterceptorType {
    APPLICATION,
    NETWORK,
    ;

    interface Provider {
        val value: InterceptorType
    }
}

fun Interceptor.activeForType(
    activeType: InterceptorType,
    typeProvider: InterceptorType.Provider,
) = Interceptor { chain ->
    if (activeType == typeProvider.value) {
        intercept(chain)
    } else {
        chain.proceed(chain.request())
    }
}

fun createAuthorizationInterceptor(prefHelper: PrefHelper): Interceptor {
    return Interceptor { chain ->
        val newBuilder = chain.request().newBuilder()
        val accessToken = prefHelper.getAccessToken()
        val refreshToken = prefHelper.getRefreshToken()
        if (accessToken != null) {
            newBuilder.addHeader("Authorization", StringConstants.bearerHeader + accessToken)
        } else if (refreshToken != null) {
            newBuilder.addHeader("Authorization", StringConstants.bearerHeader + refreshToken)
        }
        return@Interceptor chain.proceed(newBuilder.build())
    }
}