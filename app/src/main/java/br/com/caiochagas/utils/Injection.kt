package br.com.caiochagas.utils

import br.com.caiochagas.App
import br.com.caiochagas.data.source.Repository
import br.com.caiochagas.data.source.remote.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injection {

    fun repository() = Repository(App.db.dao(), apiService())

    private fun apiService() = retrofit(okHttpClient()).create(ApiService::class.java)

    private fun retrofit(client: OkHttpClient): Retrofit {

        return Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun okHttpClient() = OkHttpClient.Builder().build()
}