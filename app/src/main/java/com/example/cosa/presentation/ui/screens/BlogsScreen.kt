package com.example.cosa.presentation.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cosa.R
import com.example.cosa.presentation.ui.Components.HuertoNavbar
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

@Composable
fun BlogsScreen(navController: NavController) {
    val context = LocalContext.current

    HuertoNavbar(navController = navController) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item {
                BlogsOficiales()
            }

            item {
                BlogCard(
                    title = "Alimentación saludable",
                    text = "Descubre cómo incorporar más frutas y verduras en tu dieta diaria, disfrutando de todo su sabor y beneficios para tu salud.",
                    imageRes = R.drawable.blog1,
                    link = "https://www.youtube.com/watch?v=dQw4w9WgXcQ&list=RDdQw4w9WgXcQ&start_radio=1",
                    onLinkClick = { url ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                )
            }

            item {
                BlogCard(
                    title = "Sostenibilidad",
                    text = "Adopta un estilo de vida más sostenible con pequeñas acciones que generan un gran impacto en el planeta.",
                    imageRes = R.drawable.blog2,
                    link = "https://images7.memedroid.com/images/UPLOADED293/614398d290f70.jpeg",
                    onLinkClick = { url ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                )
            }

            item {
                BlogCard(
                    title = "Recetas",
                    text = "Explora recetas saludables y transforma ingredientes frescos en platos llenos de sabor y nutrición.",
                    imageRes = R.drawable.blog3,
                    link = "https://www.youtube.com/watch?v=A51XH7C8Xv0",
                    onLinkClick = { url ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                )
            }

            // 💬 Sección nueva: Crear y mostrar posts del usuario
            item {
                Spacer(modifier = Modifier.height(24.dp))
                UserPostsSection()
            }
        }
    }
}

@Composable
fun BlogCard(
    title: String,
    text: String,
    imageRes: Int,
    link: String,
    onLinkClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(340.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E8B57)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { onLinkClick(link) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                border = ButtonDefaults.outlinedButtonBorder,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver más")
            }
        }
    }
}

@Composable
fun BlogsOficiales() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2E8B57))
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = "🌿 Blogs oficiales de Huerto Hogar 🌿",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

// 📝 Nueva parte
@Composable
fun UserPostsSection() {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("user_posts", Context.MODE_PRIVATE)
    var posts by remember { mutableStateOf(loadPosts(prefs)) }
    var titulo by remember { mutableStateOf("") }
    var contenido by remember { mutableStateOf("") }
    val scrollState = rememberScrollState() // 👈 agregado
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🪴 Crea tu post",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = contenido,
            onValueChange = { contenido = it },
            label = { Text("Contenido") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (titulo.isNotBlank() && contenido.isNotBlank()) {
                    val newPost = Post(titulo, contenido, mutableListOf())
                    posts = listOf(newPost) + posts
                    savePosts(prefs, posts)
                    titulo = ""
                    contenido = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57))
        ) {
            Text("Publicar", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Últimos Posts",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 👇 Se reemplaza LazyColumn por un Column normal
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            posts.forEach { post ->
                PostCard(post = post) {
                    savePosts(prefs, posts)
                }
            }
        }
    }
}


data class Post(val titulo: String, val contenido: String, val comentarios: MutableList<String>)

@Composable
fun PostCard(post: Post, onCommentAdded: () -> Unit) {
    var comentario by remember { mutableStateOf("") }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(post.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(post.contenido)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Comentarios:", fontWeight = FontWeight.Medium)
            post.comentarios.forEach {
                Text("- $it", fontSize = 13.sp)
            }
            OutlinedTextField(
                value = comentario,
                onValueChange = { comentario = it },
                placeholder = { Text("Escribe un comentario") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = {
                    if (comentario.isNotBlank()) {
                        post.comentarios.add(comentario)
                        comentario = ""
                        onCommentAdded()
                    }
                },
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57))
            ) {
                Text("Enviar", color = Color.White)
            }
        }
    }
}

// Funciones para guardar/cargar posts
private fun savePosts(prefs: android.content.SharedPreferences, posts: List<Post>) {
    val jsonArray = JSONArray()
    posts.forEach { p ->
        val obj = JSONObject()
        obj.put("titulo", p.titulo)
        obj.put("contenido", p.contenido)
        obj.put("comentarios", JSONArray(p.comentarios))
        jsonArray.put(obj)
    }
    prefs.edit().putString("posts", jsonArray.toString()).apply()
}

private fun loadPosts(prefs: android.content.SharedPreferences): List<Post> {
    val jsonString = prefs.getString("posts", null) ?: return emptyList()
    val jsonArray = JSONArray(jsonString)
    val list = mutableListOf<Post>()
    for (i in 0 until jsonArray.length()) {
        val obj = jsonArray.getJSONObject(i)
        val comentariosArray = obj.getJSONArray("comentarios")
        val comentarios = mutableListOf<String>()
        for (j in 0 until comentariosArray.length()) {
            comentarios.add(comentariosArray.getString(j))
        }
        list.add(Post(obj.getString("titulo"), obj.getString("contenido"), comentarios))
    }
    return list
}
