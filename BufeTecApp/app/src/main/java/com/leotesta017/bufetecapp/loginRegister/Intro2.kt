package com.leotesta017.bufetecapp.loginRegister

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leotesta017.bufetecapp.R

@Composable
fun IntroScreen2(navController: NavController) {
    IntroScreen(
        imageRes = R.drawable.pantalla2,
        description = "Justicia al alcance de todos: asesoría jurídica gratuita con el respaldo de estudiantes y expertos",
        onNextClick = { navController.navigate("pantalla3") },
        isScreen1 = false
    )
}