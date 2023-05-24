package com.dicoding.storyu.data.network.services

import com.dicoding.storyu.utils.PreferenceManager
import com.dicoding.storyu.utils.constant.AppConstants.AUTHORIZATION
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor(
    private val requestHeaders: HashMap<String, String>,
    private val preferenceManager: PreferenceManager
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        mapRequestHeaders()
        val request = mapHeaders(chain)
        return chain.proceed(request)
    }

    private fun mapRequestHeaders() {
        val token = preferenceManager.getToken()
        requestHeaders[AUTHORIZATION] = "Bearer $token"
    }

    private fun mapHeaders(chain: Interceptor.Chain): Request {
        val original = chain.request()

        val requestBuilder = original.newBuilder()

        for ((key, value) in requestHeaders) {
            requestBuilder.addHeader(key, value)
        }
        return requestBuilder.build()
    }

}