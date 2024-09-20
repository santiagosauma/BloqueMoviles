package com.leotesta017.clinicapenal.view.loginRegister

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.R

@Composable
fun IntroScreen1(navController: NavController) {
    IntroScreen(
        imageRes = R.drawable.pantalla1,
        description = "Justicia al alcance de todos: asesoría jurídica gratuita con el respaldo de estudiantes y expertos",
        onNextClick = { navController.navigate("intro2") },
        isScreen1 = true
    )
}