package com.leotesta017.clinicapenal.loginRegister

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.R

@Composable
fun IntroScreen2(navController: NavController) {
    IntroScreen(
        imageRes = R.drawable.pantalla2,
        description = "Justicia al alcance de todos: asesoría jurídica gratuita con el respaldo de estudiantes y expertos",
        onNextClick = { navController.navigate("pantalla3") },
        isScreen1 = false
    )
}