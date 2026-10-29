package com.diegog.proyectoconcesionaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.diegog.proyectoconcesionaria.ui.theme.AppTheme
import com.diegog.proyectoconcesionaria.ui.theme.ProyectoLokiTheme

class TerminosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.isNavigationBarContrastEnforced = false
        setContent {
            ProyectoLokiTheme {

                androidx.compose.material3.Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        BarraSuperiorApp(
                            titulo = "Términos y Condiciones",
                            onBackClick = { finish() }
                        )
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        TerminosScreen(
                            onCerrarClick = {
                                finish()
                            }
                        )
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminosScreen(
    onCerrarClick: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.spacing.medium)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            stringResource(R.string.terminos_condiciones),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(AppTheme.spacing.medium))
        Text(stringResource(R.string.texto_terminos))
        Spacer(modifier = Modifier.height(AppTheme.spacing.extraLarge))
        Button(
            onClick = onCerrarClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.aceptar_continuar))
        }
    }
}