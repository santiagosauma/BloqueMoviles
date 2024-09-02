package com.leotesta017.clinicapenal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pantallainformacion.Pantalla
import com.leotesta017.clinicapenal.loginRegister.*
import com.leotesta017.clinicapenal.usuarioColaborador.Pantalla13
import com.leotesta017.clinicapenal.usuarioGeneral.PantallaInfo
import com.leotesta017.clinicapenal.solicitud.GeneralSolicitud
import com.leotesta017.clinicapenal.usuarioColaborador.PantallaInfoAdmin
import com.leotesta017.clinicapenal.usuarioGeneral.DetalleInfo
import com.leotesta017.clinicapenal.usuarioGeneral.ReviewComentarios
import com.leotesta017.clinicapenal.usuarioGeneral.ServiciosInfo
import com.leotesta017.clinicapenal.usuarioGeneral.Solicitud

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
        composable("pantalla11") { Pantalla(navController) }
        composable("pantalla12") { PantallaInfo(navController) }
        composable("pantalla13") { Pantalla13() }
        composable("solicitud") { GeneralSolicitud(navController) }
        composable("ReviewComentarios") { ReviewComentarios(navController) }
        composable("detalle_info") { DetalleInfo(navController) }
        composable("crearsolicitud") { Solicitud(navController) }
        composable("servicios_info") { ServiciosInfo(navController)}
        composable("pantallainfoadmin") { PantallaInfoAdmin(navController)}
    }
}
