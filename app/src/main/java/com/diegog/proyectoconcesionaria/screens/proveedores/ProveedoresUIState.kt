package com.diegog.proyectoconcesionaria.screens.proveedores

import com.diegog.proyectoconcesionaria.models.Proveedor

sealed interface ProveedoresUIState {
    data object Loading: ProveedoresUIState
    data class Success(val proveedores: List<Proveedor>): ProveedoresUIState
    data class Error(val mensaje: String): ProveedoresUIState
}