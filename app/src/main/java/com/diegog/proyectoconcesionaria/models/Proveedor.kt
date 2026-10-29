package com.diegog.proyectoconcesionaria.models

data class Proveedor(
    val id_vehiculo: Int = 0,
    val modelo: String = "",
    val anio: Int = 0,
    val precio: Int = 0,
    val condicion: String = "",
    val carroceria: String = "",
    val transmision: String = "",
    val combustible: String = ""
)