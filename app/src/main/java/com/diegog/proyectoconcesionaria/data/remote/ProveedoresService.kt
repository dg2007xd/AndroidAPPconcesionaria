package com.diegog.proyectoconcesionaria.data.remote

import com.diegog.proyectoconcesionaria.models.Proveedor
import retrofit2.http.GET

interface ProveedoresService {
    @GET("vehiculos.php")
    suspend fun getProveedores(): List<Proveedor>
}