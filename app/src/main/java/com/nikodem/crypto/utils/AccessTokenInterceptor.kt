package com.nikodem.crypto.utils

import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val modifiedRequest = request.newBuilder()
            .header("x-access-token", "coinrankingd0e0bd7be68cdfcfac438e3308a4b545d6d3e859657c0663")
            .build()

        return chain.proceed(modifiedRequest);
    }
}