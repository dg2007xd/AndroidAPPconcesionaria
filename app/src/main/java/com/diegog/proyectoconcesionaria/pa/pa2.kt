package com.diegog.proyectoconcesionaria.pa

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Input (value: String, onValueChange: (String) -> Unit, label: String){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {Text(label)},
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    )
}

@Composable
fun Card(titulo: String, precio: String, onOfertaClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
       Column(modifier = Modifier.padding(16.dp)) {
           Text(titulo, style = MaterialTheme.typography.titleMedium)
           Spacer(modifier = Modifier.height(4.dp))
           Text(precio, style = MaterialTheme.typography.bodyMedium)
           Spacer(modifier = Modifier.height(8.dp))
           Button(onClick = onOfertaClick) {
               Text("Haz una oferta")
           }
       }
    }
}

@Composable
fun VistaCatalogo(onNavigateToForm: () -> Unit, onOpenModal: () -> Unit) {
    val autos = listOf("Huracan EVO" to "$260,000", "Kia Seltos" to "$450000")
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Catalogo de Vehiculos", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateToForm) { Text("It a vista de inputs")}
        Spacer(modifier = Modifier. height(8.dp))

        LazyColumn {
            items(autos) { auto ->
                Card(titulo = auto.first, precio = auto.second, onOfertaClick = onOpenModal)
            }
        }
    }
}

@Composable
fun VistaFormulario(onBack: () -> Unit) {
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Resgistro / Filtro", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        Input(value = marca, onValueChange = { marca = it }, label = "Marca")
        Input(value = modelo, onValueChange = { marca = it }, label = "Modelo")

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) {Text("Regresar al catalogo") }
    }
}


@Composable
fun PA(){
    var pantallaActual by remember { mutableStateOf("catalogo")}
    var mostrarModal by remember { mutableStateOf(false)}

    Box(modifier = Modifier.fillMaxSize().padding(top = 24.dp)) {
        when(pantallaActual) {
            "catalogo" -> VistaCatalogo(
                    onNavigateToForm = { pantallaActual = "formulario"},
                    onOpenModal = { mostrarModal = true}
                    )
            "formulario" -> VistaFormulario (
                onBack = { pantallaActual = "catalogo"}
            )


        }


        if(mostrarModal) {
            AlertDialog(
                onDismissRequest = { mostrarModal = false},
                title = {Text("Enviar Oferta")},
                text = {
                    Column {
                        Text("Ingresa tu oferta para este vehiculo:")
                        Spacer(modifier = Modifier.height(8.dp))
                        Input(value = "", onValueChange = {}, label = "Monto USD")
                    }
                },
                confirmButton = {
                    Button(onClick = { mostrarModal = false}) { Text("Confirmar") }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarModal = false}) { Text("Cancelar") }
                }
            )
        }
    }

}