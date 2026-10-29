package com.diegog.proyectoconcesionaria.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .callTimeout(30, TimeUnit.SECONDS)
        .build()


   private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://dg2104dx.alwaysdata.net/autos/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val proveedoresService: ProveedoresService by lazy { retrofit.create() }
    //val oficinasService: OficinasService by lazy { retrofit.create() }
    //val empleadosService: EmpleadosService by lazy { retrofit.create() }
}