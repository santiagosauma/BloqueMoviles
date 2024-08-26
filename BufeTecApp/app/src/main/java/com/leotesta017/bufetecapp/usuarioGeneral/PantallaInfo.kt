package com.leotesta017.bufetecapp.usuarioGeneral

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PantallaInfo(navController: NavController?) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                TopBar()
                Spacer(modifier = Modifier.height(16.dp))
                SearchBar("")
                Spacer(modifier = Modifier.height(16.dp))
                InfoLegalSection()
                Spacer(modifier = Modifier.height(16.dp))
                CategoriesSection()
            }
        }

        BarraNav(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Clínica Penal",
            fontSize = 38.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Blue
        )
    }
}

@Composable
fun SearchBar(searchText: String) {
    OutlinedTextField(
        value = searchText,
        onValueChange = {},
        label = { Text("Buscar...") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
        singleLine = true
    )
}

@Composable
fun InfoLegalSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Información Legal",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun CategoriesSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        CategoryItem("Robo y hurto", "La toma de propiedad ajena sin el consentimiento del propietario, con la intención de no devolverla.")
        Spacer(modifier = Modifier.height(8.dp))
        CategoryItem("Extorsión y amenaza", "Amenazas o coerción para obtener dinero, bienes o servicios de una persona mediante la intimidación.")
        Spacer(modifier = Modifier.height(8.dp))
        CategoryItem("Violencia Doméstica", "Abuso físico, emocional o psicológico dentro del entorno familiar, destinado a controlar o intimidar a un miembro del hogar.")
        Spacer(modifier = Modifier.height(8.dp))
        CategoryItem("Deudas", "Falta de pago de una obligación financiera que puede derivar en acciones legales por parte del acreedor.")
    }
}

@Composable
fun CategoryItem(title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Text(text = description, fontSize = 14.sp, color = Color.Black, modifier = Modifier.weight(1f))
            Text(text = "Ver más", fontSize = 14.sp, color = Color.Blue, modifier = Modifier.padding(start = 8.dp))
        }
    }
}
