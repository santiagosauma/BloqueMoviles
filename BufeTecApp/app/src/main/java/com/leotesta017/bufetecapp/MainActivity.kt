package com.leotesta017.bufetecapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pantallainformacion.Pantalla
import com.leotesta017.bufetecapp.loginRegister.*
import com.leotesta017.bufetecapp.usuarioColaborador.Pantalla13
import com.leotesta017.bufetecapp.usuarioEstudiante.Pantalla12
import com.leotesta017.bufetecapp.usuarioGeneral.Pantalla11

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "intro1") {
        composable("intro1") { IntroScreen1(navController) }
        composable("intro2") { IntroScreen2(navController) }
        composable("pantalla3") { Pantalla3(navController) }
        composable("pantalla4") { Pantalla4(navController) }
        composable("pantalla5") { Pantalla5(navController) }
        composable("pantalla6") { Pantalla6(navController) }
        composable("pantalla7") { Pantalla7(navController) }
        composable("pantalla8") { Pantalla8(navController) }
        composable("pantalla9") { Pantalla9(navController) }
        composable("pantalla10") { Pantalla10(navController) }
        composable("pantalla11") { Pantalla() }  // Nueva pantalla de usuario general
        composable("pantalla12") { Pantalla12() }  // Nueva pantalla de estudiante
        composable("pantalla13") { Pantalla13() }  // Nueva pantalla de colaborador
    }
}