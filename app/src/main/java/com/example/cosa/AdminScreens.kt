package com.example.cosa

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cosa.data.database.AppDatabase
import com.example.cosa.data.model.Producto
import com.example.cosa.data.model.Usuario
import com.example.cosa.data.repository.ProductoRepository
import com.example.cosa.data.repository.UsuarioRepository
import kotlinx.coroutines.launch

// Pantalla principal del admin: botones para navegar a cada sección
@Composable
fun AdminMainScreen(navController: NavHostController, sessionViewModel: com.example.cosa.presentation.viewmodel.SessionViewModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Panel de Administrador", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { navController.navigate("admin/productos") }, modifier = Modifier.fillMaxWidth()) {
            Text("Productos")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("admin/usuarios") }, modifier = Modifier.fillMaxWidth()) {
            Text("Usuarios")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("admin/ventas") }, modifier = Modifier.fillMaxWidth()) {
            Text("Ventas")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("admin/documentos") }, modifier = Modifier.fillMaxWidth()) {
            Text("Documentos")
        }
    }
}

// Usuarios admin: carga usuarios desde Room a través de UsuarioRepository
@Composable
fun UsuariosAdminScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var usuarios by remember { mutableStateOf<List<Usuario>>(emptyList()) }

    LaunchedEffect(Unit) {
        val db = AppDatabase.getDatabase(context)
        val repo = UsuarioRepository(db.usuarioDao())
        scope.launch {
            usuarios = db.usuarioDao().getAll()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Text("Usuarios", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(usuarios) { u ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = u.usuario, fontWeight = FontWeight.Bold)
                            Text(text = u.correo)
                        }
                        Text(text = "ID: ${u.id}")
                    }
                }
            }
        }
    }
}

// Productos admin: carga productos desde ProductoRepository (datos en memoria existentes)
@Composable
fun ProductosAdminScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    var productos by remember { mutableStateOf<List<Producto>>(emptyList()) }

    LaunchedEffect(Unit) {
        val repo = ProductoRepository()
        scope.launch {
            productos = repo.obtenerProductos()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Text("Productos", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(productos) { p ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { /* futuro: editar producto */ }) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = p.nombre, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Precio: $${p.precio}")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = p.descripcion, maxLines = 2)
                    }
                }
            }
        }
    }
}

// Ventas admin: plantilla simple
@Composable
fun VentasAdminScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Ventas", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Aquí puedes mostrar las ventas registradas o reportes.")
    }
}

// Documentos admin: lista archivos en assets/documentos (si los tuvieras) o muestra PDFs incluidos
@Composable
fun DocumentosAdminScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Documentos", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Lista de documentos disponibles: puedes colocar PDFs en assets/documentos y listarlos aquí.")
    }
}
