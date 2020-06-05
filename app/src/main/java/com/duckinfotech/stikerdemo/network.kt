package com.duckinfotech.stikerdemo

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("{stickerPackUrl}")
    fun getStickerPack(@Path("stickerPackUrl") stickerPackUrl: String): Call<ResponseBody>

}

object Network {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://static.stickify.app/")
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}