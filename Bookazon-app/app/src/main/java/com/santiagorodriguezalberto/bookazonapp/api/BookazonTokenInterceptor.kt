package com.santiagorodriguezalberto.bookazonapp.api

import com.santiagorodriguezalberto.bookazonapp.common.Constantes
import com.santiagorodriguezalberto.bookazonapp.common.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookazonTokenInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val request: Request
        val token = SharedPreferencesManager().getSharedPreferences()
            .getString(Constantes.SHARED_PREFERENCES_TOKEN_KEYWORD, "")

        val requestBuilder: Request.Builder =
            original.newBuilder().header("Authorization", "Bearer " + token)
        request = requestBuilder.build()


        return chain.proceed(request)

    }
}