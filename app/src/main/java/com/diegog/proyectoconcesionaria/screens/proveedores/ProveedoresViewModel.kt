package com.diegog.proyectoconcesionaria.screens.proveedores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegog.proyectoconcesionaria.data.repository.ProveedoresRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProveedoresViewModel (
    private val repository: ProveedoresRepository = ProveedoresRepository()): ViewModel()
{
    private val _uiState = MutableStateFlow<ProveedoresUIState>(ProveedoresUIState.Loading)
    val uiState: StateFlow<ProveedoresUIState> = _uiState.asStateFlow()

    fun fetchProveedores(){
        viewModelScope.launch {
            _uiState.value = ProveedoresUIState.Loading
            _uiState.value = try {
                ProveedoresUIState.Success(repository.listProveedores())
            } catch (e: CancellationException){
                throw e
            } catch (e: Exception){
                ProveedoresUIState.Error(
                    "No se pudieron cargar los datos. Revise su conexión o intente de nuevo: "
                            + e.message)
            }
        }
    }
    init {
        fetchProveedores()
    }
}