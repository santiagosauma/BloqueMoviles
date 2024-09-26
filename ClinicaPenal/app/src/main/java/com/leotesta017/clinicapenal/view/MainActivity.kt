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
import com.leotesta017.clinicapenal.view.usuarioColaborador.ModificarServiciosInfoAdmin
import com.leotesta017.clinicapenal.view.usuarioColaborador.PantallaInfoAdmin
import com.leotesta017.clinicapenal.view.usuarioColaborador.PantallaInfoClinicaAdmin
import com.leotesta017.clinicapenal.view.usuarioEstudiante.AgregarInfoEstudiante
import com.leotesta017.clinicapenal.view.usuarioEstudiante.AgregarServiciosInfoEstudiante
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

        /*=======================================================================================*/
        // PANTALLAS DE LOGIN
        /*=======================================================================================*/

        //PRIMERAS DOS PANTALlAS DE CARGA
        composable("intro1") { IntroScreen1(navController) }
        composable("intro2") { IntroScreen2(navController) }

        //PANTALLA PARA ESCOGER SI INCIAR SESION O CREAR CUENTA
        composable("pantalla3") { Pantalla3(navController) }

        //PANTALLA PARA CREAR CUENTA
        composable("pantalla4") { Pantalla4(navController) }

        //PANTALLA PARA INICIAR SESION
        composable("pantalla5") { Pantalla5(navController) }


        //PANTALLA PARA ESCOGER SI CREAR CUENTA DE ESTUDIANTE O DE ADMIN
        composable("pantalla6") { Pantalla6(navController) }


        composable("pantalla7") { Pantalla7(navController) }
        composable("pantalla8") { Pantalla8(navController) }
        composable("pantalla9") { Pantalla9(navController) }
        composable("pantalla10") { Pantalla10(navController) }


        /*=======================================================================================*/
        // PANTALLAS DE USUARIO GENERAL
        /*=======================================================================================*/
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

        /*=======================================================================================*/
        // PANTALLAS DE ADMIN
        /*=======================================================================================*/
        composable("pantallainfoadmin") { PantallaInfoAdmin(navController)}

        composable(
            route = "modificar-info/{titulo}/{descripcion}/{categoriaId}",
            arguments = listOf(
                navArgument("titulo"){type = NavType.StringType},
                navArgument("descripcion"){type = NavType.StringType},
                navArgument("categoriaId"){type = NavType.StringType})
        ) { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
            val descripcion = backStackEntry.arguments?.getString("descripcion") ?: ""
            val servicioId = backStackEntry.arguments?.getString("categoriaId") ?: ""

            ModificarInfoAdmin(
                navController = navController,
                id = servicioId,
                titulo = titulo,
                descripcion = descripcion
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

            ModificarServiciosInfoAdmin(
                navController = navController,
                id = servicioId,
                titulo = titulo,
                descripcion = descripcion
            )
        }

        composable("agregar-info-admin") { AgregarInfoAdmin(navController) }
        composable("agregar_servicios_info-admin") { AgregarServiciosInfoAdmin(navController) }
        composable("generalsolicitudadmin") { GeneralSolicitudAdmin(navController)}
        composable("actualizarcasos") {ActualizarCasos(navController)}
        composable("infoclinicaadmin") { PantallaInfoClinicaAdmin(navController) }
        composable("agendar") { Agendar(navController) }
        composable("comentar") { ComentarioScreen(navController) }

        //PANTALLA DEL JURIBOT PARA ADMIN
        composable("JuriBotAdmin") { JuriBotAdmin(navController) }




        /*=======================================================================================*/
        // PANTALLAS DE ESTUDIANTE
        /*=======================================================================================*/
        //PANTALLA DE INICIO DE ESTUDIANTE
        composable("pantallainfoestudiante"){ PantallaInfoEstudiante(navController)}

        //PANTALLA DE HISTORIAL DE SOLICITUDES
        composable("generasolicitudestudiante"){ GenerarSolicitudEstudiante(navController) }

        //PANTALLA DE DATOS DE LA CLINICA
        composable("informacionestudiate"){ InformacionClinicaEstudiante(navController) }

        //PANTALLA DE VISTA DETALLADA DEL CASO
        composable("detallecasoestudiante") { DetalleCasoEstudiante(navController) }

        //AGREGAR CATEGORIA Y SERVICIOS ESTUDIANTES
        composable("agregar-info-estudiante") { AgregarInfoEstudiante(navController)}
        composable("agregar-servicio-estudiante") { AgregarServiciosInfoEstudiante(navController) }

    }
}

