package br.com.caiochagas.data.source.remote

import br.com.caiochagas.BuildConfig
import br.com.caiochagas.data.model.Message
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET(BuildConfig.ENDPOINT)
    fun getAll(): Call<List<Message>>
}