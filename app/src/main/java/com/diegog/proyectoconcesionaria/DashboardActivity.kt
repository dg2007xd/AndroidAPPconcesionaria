package com.diegog.proyectoconcesionaria

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.TextSnippet
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.diegog.proyectoconcesionaria.screens.DetalleActivity
import com.diegog.proyectoconcesionaria.screens.proveedores.ProveedoresActivity
import com.diegog.proyectoconcesionaria.ui.theme.ProyectoLokiTheme
import com.diegog.proyectoconcesionaria.screens.PerfilActivity
import com.diegog.proyectoconcesionaria.screens.tienda.TiendaActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class Vehiculo(
    val id_vehiculo: Int = 0,
    val modelo: String = "",
    val precio: Int = 0,
    val carroceria: String = "",
    val transmision: String = "",
    val id_marca: Int = 0,
    val asientos: Int = 0,
    val imagen_tarjeta_url: String = "",
    // Añadimos las 4 columnas exactas de tu BD:
    val imagen_detallef_url: String = "",
    val imagen_detallet_url: String = "",
    val imagen_detallei_url: String = "",
    val imagen_detalled_url: String = ""
)

data class Marca(
    val id_marca: Int = 0,
    val nombre: String = "",
    val logo_url: String = ""
)

interface ApiService {
    @GET("vehiculos.php")
    suspend fun getVehiculos(): List<Vehiculo>

    @GET("marcas.php")
    suspend fun getMarcas(): List<Marca>
}

object RetrofitClient {
    val baseUrl = "https://dg2104dx.alwaysdata.net/autos/"
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }
}

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoLokiTheme {
                // Usamos Scaffold para mantener la barra arriba y el contenido abajo sin sobreponerse
                androidx.compose.material3.Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        BarraSuperiorApp(
                            titulo = "Panel Principal",
                            onBackClick = { finish() }
                        )
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding), // innerPadding respeta perfectamente el espacio de la barra
                        color = MaterialTheme.colorScheme.background
                    ) {
                        DashboardScreen(
                            onProfileClick = {
                                startActivity(Intent(this, PerfilActivity::class.java))
                            },
                            onWishlistClick = {},
                            onNotificationsClick = {},
                            onMakeOfferClick = {
                                startActivity(Intent(this, ProveedoresActivity::class.java))
                            },
                            onNavigateHome = {},
                            onNavigateCart = {
                                startActivity(Intent(this, TiendaActivity::class.java))
                            },
                            onNavigateChat = {},
                            onNavigateCards = {},
                            onNavigateProfile = {
                                startActivity(Intent(this, PerfilActivity::class.java))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardScreen(
    onProfileClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onMakeOfferClick: () -> Unit,
    onNavigateHome: () -> Unit,
    onNavigateCart: () -> Unit,
    onNavigateChat: () -> Unit,
    onNavigateCards: () -> Unit,
    onNavigateProfile: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    var vehiculos by remember { mutableStateOf<List<Vehiculo>>(emptyList()) }
    var marcas by remember { mutableStateOf<List<Marca>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            vehiculos = RetrofitClient.api.getVehiculos()
            marcas = RetrofitClient.api.getMarcas()
        } catch (e: Exception) {
            error = true
            e.printStackTrace()
        } finally {
            cargando = false
        }
    }

    val vehiculosFiltrados = if (selectedCategory == "All") {
        vehiculos
    } else {
        val marcaSeleccionada = marcas.find { it.nombre.equals(selectedCategory, ignoreCase = true) }
        if (marcaSeleccionada != null) {
            vehiculos.filter { it.id_marca == marcaSeleccionada.id_marca }
        } else {
            vehiculos
        }
    }.filter {
        it.modelo.contains(searchQuery, ignoreCase = true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onProfileClick() }
                ) {
                    Surface(
                        shape = CircleShape,
                        modifier = Modifier.size(48.dp),
                        color = Color.LightGray
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "Avatar",
                                tint = Color.DarkGray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = "Donna Freeman", style = MaterialTheme.typography.titleMedium)
                        Text(text = "📍 United States", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface,
                        shadowElevation = 1.dp,
                        modifier = Modifier.size(44.dp).clickable { onWishlistClick() }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Wishlist", tint = MaterialTheme.colorScheme.onSurface)
                        }
                    }

                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface,
                        shadowElevation = 1.dp,
                        modifier = Modifier.size(44.dp).clickable { onNotificationsClick() }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Outlined.Notifications, contentDescription = "Notificaciones", tint = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search", style = MaterialTheme.typography.bodyMedium) },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    leadingIcon = {
                        Icon(Icons.Outlined.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    },
                    shape = RoundedCornerShape(28.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    ),
                    modifier = Modifier.weight(1f).height(52.dp)
                )

                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.size(52.dp).clickable { }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Outlined.Tune, contentDescription = "Filtros", tint = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isAllSelected = selectedCategory == "All"
                Button(
                    onClick = { selectedCategory = "All" },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isAllSelected) Color.Black else MaterialTheme.colorScheme.surface,
                        contentColor = if (isAllSelected) Color.White else MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.height(44.dp)
                ) {
                    Text(text = "All", style = MaterialTheme.typography.labelMedium)
                }

                marcas.forEach { marca ->
                    val isSelected = selectedCategory == marca.nombre
                    Button(
                        onClick = { selectedCategory = marca.nombre },
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) Color.Black else MaterialTheme.colorScheme.surface,
                            contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.height(44.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (marca.logo_url.isNotBlank()) {
                                // CONTENEDOR UNIFORME PARA EL LOGO
                                Box(
                                    modifier = Modifier.size(32.dp), // <--- Tamaño exacto y uniforme para la cajita del logo
                                    contentAlignment = Alignment.Center
                                ) {
                                    AsyncImage(
                                        model = if (marca.logo_url.isNotBlank() && marca.logo_url.startsWith("http"))
                                            marca.logo_url
                                        else
                                            "${RetrofitClient.baseUrl}${marca.logo_url}",
                                        contentDescription = marca.nombre,
                                        modifier = Modifier.fillMaxSize(), // Llena la caja de 32x32 manteniendo la escala
                                        contentScale = ContentScale.Fit // Evita que se estiren o recorten feo
                                    )
                                }
                            }
                            Text(text = marca.nombre, style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                when {
                    cargando -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.primary)
                    }
                    error -> {
                        Text(text = "Error al cargar los datos", modifier = Modifier.align(Alignment.Center), style = MaterialTheme.typography.bodyMedium, color = Color.Red)
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 100.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(vehiculosFiltrados, key = { it.id_vehiculo }) { vehiculo ->
                                CarCardItem(
                                    title = vehiculo.modelo,
                                    specsContent = {
                                        Column(horizontalAlignment = Alignment.End) {
                                            Text(text = "${vehiculo.asientos} Asientos", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                            Text(text = vehiculo.transmision, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    },
                                    price = "$${vehiculo.precio}",
                                    imageUrl = vehiculo.imagen_tarjeta_url,
                                    onMakeOfferClick = onMakeOfferClick,
                                    onCardClick = {
                                        // Creamos la lista agrupando las 4 imágenes de detalle
                                        val listaImagenes = arrayListOf(
                                            vehiculo.imagen_detallef_url,
                                            vehiculo.imagen_detallet_url,
                                            vehiculo.imagen_detallei_url,
                                            vehiculo.imagen_detalled_url
                                        )

                                        val intent = Intent(context, DetalleActivity::class.java).apply {
                                            putExtra("EXTRA_MODELO", vehiculo.modelo)
                                            putExtra("EXTRA_PRECIO", "$${vehiculo.precio}")
                                            putStringArrayListExtra("EXTRA_GALERIA", listaImagenes) // <--- Enviamos las 4 juntas
                                            putExtra("EXTRA_ASIENTOS", vehiculo.asientos)
                                            putExtra("EXTRA_TRANSMISION", vehiculo.transmision)
                                        }
                                        context.startActivity(intent)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // CÍRCULOS FLOTANTES MÁS GRANDES Y PERFECTAMENTE CENTRADOS
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Home (Círculo Negro - Seleccionado)
            Surface(
                shape = CircleShape,
                color = Color.Black,
                shadowElevation = 6.dp,
                modifier = Modifier
                    .size(60.dp)
                    .clickable { onNavigateHome() }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Outlined.Home,
                        contentDescription = "Home",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            // 2. Tienda (Círculo Blanco)
            Surface(
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 6.dp,
                modifier = Modifier
                    .size(60.dp)
                    .clickable { onNavigateCart() }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Outlined.ShoppingCart,
                        contentDescription = "Tienda",
                        tint = Color.Gray,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            // 3. Chat (Círculo Blanco)
            Surface(
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 6.dp,
                modifier = Modifier
                    .size(60.dp)
                    .clickable { onNavigateChat() }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Outlined.TextSnippet,
                        contentDescription = "Chat",
                        tint = Color.Gray,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            // 4. Cards (Círculo Blanco)
            Surface(
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 6.dp,
                modifier = Modifier
                    .size(60.dp)
                    .clickable { onNavigateCards() }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Outlined.CreditCard,
                        contentDescription = "Cards",
                        tint = Color.Gray,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            // 5. Perfil (Círculo Blanco)
            Surface(
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 6.dp,
                modifier = Modifier
                    .size(60.dp)
                    .clickable { onNavigateProfile() }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = "Perfil",
                        tint = Color.Gray,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CarCardItem(
    title: String,
    specsContent: @Composable () -> Unit,
    price: String,
    imageUrl: String,
    onMakeOfferClick: () -> Unit,
    onCardClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth().clickable { onCardClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(text = title, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = "⭐ 4.9 (Vehículo destacado)", style = MaterialTheme.typography.bodySmall, color = Color(0xFFFFA000))
                }

                specsContent()
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (imageUrl.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = if (imageUrl.startsWith("http")) imageUrl else "${RetrofitClient.baseUrl}${imageUrl}",
                        contentDescription = title,
                        // Fit mantiene la proporción real del carro sin deformarlo ni recortarlo
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = price, style = MaterialTheme.typography.titleLarge)

                Button(
                    onClick = onMakeOfferClick,
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White),
                    modifier = Modifier.height(44.dp)
                ) {
                    Text(text = "Make an Offer 🛒", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}