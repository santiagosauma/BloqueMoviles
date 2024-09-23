package com.leotesta017.clinicapenal.view

import JuriBotScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pantallainformacion.PantallaInfoClinica
import com.leotesta017.clinicapenal.view.loginRegister.*
import com.leotesta017.clinicapenal.solicitud.GeneralSolicitud
import com.leotesta017.clinicapenal.view.usuarioColaborador.ActualizarCasos
import com.leotesta017.clinicapenal.view.usuarioColaborador.Agendar
import com.leotesta017.clinicapenal.view.usuarioColaborador.AgregarInfoAdmin
import com.leotesta017.clinicapenal.view.usuarioColaborador.AgregarServiciosInfoAdmin
import com.leotesta017.clinicapenal.view.usuarioColaborador.ComentarioScreen
import com.leotesta017.clinicapenal.view.usuarioColaborador.GeneralSolicitudAdmin
import com.leotesta017.clinicapenal.view.usuarioColaborador.JuriBotAdmin
import com.leotesta017.clinicapenal.view.usuarioColaborador.ModificarInfoAdmin
import com.leotesta017.clinicapenal.view.usuarioColaborador.ModificarServiciosAdmin
import com.leotesta017.clinicapenal.view.usuarioColaborador.PantallaInfoAdmin
import com.leotesta017.clinicapenal.view.usuarioColaborador.PantallaInfoClinicaAdmin
import com.leotesta017.clinicapenal.view.usuarioEstudiante.DetalleCasoEstudiante
import com.leotesta017.clinicapenal.view.usuarioEstudiante.GenerarSolicitudEstudiante
import com.leotesta017.clinicapenal.view.usuarioEstudiante.InformacionClinicaEstudiante
import com.leotesta017.clinicapenal.view.usuarioEstudiante.PantallaInfoEstudiante
import com.leotesta017.clinicapenal.view.usuarioGeneral.DetalleInfo
import com.leotesta017.clinicapenal.view.usuarioGeneral.PantallaInfoCategorias
import com.leotesta017.clinicapenal.view.usuarioGeneral.ReviewComentarios
import com.leotesta017.clinicapenal.view.usuarioGeneral.SegundoFormulario
import com.leotesta017.clinicapenal.view.usuarioGeneral.ServiciosInfo
import com.leotesta017.clinicapenal.view.usuarioGeneral.Solicitud

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


        //Pantallas Usuario General
        composable("pantallainfocategoriasgeneral") { PantallaInfoCategorias (navController) }
        composable("pantallainformacionclinica") { PantallaInfoClinica(navController) }
        composable("solicitud") { GeneralSolicitud(navController) }
        composable("ReviewComentarios") { ReviewComentarios(navController) }
        composable("crearsolicitud") { Solicitud(navController) }

        composable(
            route = "detalle_info/{titulo}/{descripcion}/{categoriaId}",
            arguments = listOf(
                navArgument("titulo"){type = NavType.StringType},
                navArgument("descripcion"){type = NavType.StringType},
                navArgument("categoriaId"){type = NavType.StringType}

                )

        ) { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
            val descripcion = backStackEntry.arguments?.getString("descripcion") ?: ""
            val categoriaId = backStackEntry.arguments?.getString("categoriaId") ?: ""
            DetalleInfo(
                navController = navController,
                titulo = titulo,
                descripcion = descripcion,
                categoriaId = categoriaId
            )
        }


        composable(
            route = "servicios_info/{titulo}/{descripcion}/{servicioId}" ,
            arguments = listOf(
                navArgument("titulo"){type = NavType.StringType},
                navArgument("descripcion"){type = NavType.StringType},
                navArgument("servicioId"){type = NavType.StringType})
        ) { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
            val descripcion = backStackEntry.arguments?.getString("descripcion") ?: ""
            val servicioId = backStackEntry.arguments?.getString("servicioId") ?: ""

            ServiciosInfo(
                navController = navController,
                titulo = titulo,
                descripcion = descripcion,
                servicioId = servicioId
            )
        }



        composable("Juribot") { JuriBotScreen(navController)}
        composable("SegundoFormulario") { SegundoFormulario(navController)}

        //Pantallas Usuario Admin
        composable("pantallainfoadmin") { PantallaInfoAdmin(navController)}

        composable(
            route = "modificar-info/{titulo}/{descripcion}/{categoriaId}",
            arguments = listOf(
                navArgument("titulo"){type = NavType.StringType},
                navArgument("descripcion"){type = NavType.StringType},
                navArgument("servicioId"){type = NavType.StringType})
        ) { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
            val descripcion = backStackEntry.arguments?.getString("descripcion") ?: ""
            val servicioId = backStackEntry.arguments?.getString("servicioId") ?: ""

            ModificarInfoAdmin(
                navController
            )
        }

        composable(
            route ="modificar_servicios_info/{titulo}/{descripcion}/{servicioId}",
            arguments = listOf(
                navArgument("titulo"){type = NavType.StringType},
                navArgument("descripcion"){type = NavType.StringType},
                navArgument("servicioId"){type = NavType.StringType})
        ) { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
            val descripcion = backStackEntry.arguments?.getString("descripcion") ?: ""
            val servicioId = backStackEntry.arguments?.getString("servicioId") ?: ""

            ModificarServiciosAdmin(
                navController
            )
        }

        composable("agregar-info-admin") { AgregarInfoAdmin(navController) }
        composable("agregar_servicios_info-admin") { AgregarServiciosInfoAdmin(navController) }
        composable("generalsolicitudadmin") { GeneralSolicitudAdmin(navController)}
        composable("actualizarcasos") {ActualizarCasos(navController)}
        composable("infoclinicaadmin") { PantallaInfoClinicaAdmin(navController) }
        composable("agendar") { Agendar(navController) }
        composable("comentar") { ComentarioScreen(navController) }
        composable("JuriBotAdmin") { JuriBotAdmin(navController) }

        //Pantalla Usuario Estudiante
        composable("pantallainfoestudiante"){ PantallaInfoEstudiante(navController)}
        composable("generasolicitudestudiante"){ GenerarSolicitudEstudiante(navController) }
        composable("informacionestudiate"){ InformacionClinicaEstudiante(navController) }
        composable("detallecasoestudiante") { DetalleCasoEstudiante(navController) }

    }
}

