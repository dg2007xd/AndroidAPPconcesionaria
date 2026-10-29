package com.diegog.proyectoconcesionaria.data.repository

import com.diegog.proyectoconcesionaria.data.remote.ProveedoresService
import com.diegog.proyectoconcesionaria.data.remote.RetrofitClient
import com.diegog.proyectoconcesionaria.models.Proveedor

class ProveedoresRepository(
    private val service: ProveedoresService = RetrofitClient.proveedoresService
) {
    suspend fun listProveedores(): List<Proveedor> = service.getProveedores()
}