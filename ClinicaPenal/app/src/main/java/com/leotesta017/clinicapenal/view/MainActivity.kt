package com.leotesta017.clinicapenal.view

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.traceEventStart
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.leotesta017.clinicapenal.view.loginRegister.*
import com.leotesta017.clinicapenal.solicitud.GeneralSolicitud
import com.leotesta017.clinicapenal.view.usuarioColaborador.*
import com.leotesta017.clinicapenal.view.usuarioEstudiante.*
import com.leotesta017.clinicapenal.view.usuarioGeneral.*
import com.leotesta017.clinicapenal.view.editvideo.EditVideoScreen

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
        composable("pantallainfocategoriasgeneral") { PantallaInfoCategorias(navController) }

        composable("pantallainformacionclinica") { PantallaInfoClinica(navController) }
        composable("solicitud") { GeneralSolicitud(navController) }
        composable(
            route = "ReviewComentarios/{case_id}",
            arguments = listOf(
                navArgument("case_id") { type = NavType.StringType },
            )
        ) { backStackEntry ->

            val case_id = backStackEntry.arguments?.getString("case_id") ?: ""
            ReviewComentarios(
                navController = navController,
                case_id = case_id,
            )
        }
        composable("crearsolicitud") { Solicitud(navController) }

        composable(
            route = "detalle_info/{titulo}/{descripcion}/{categoriaId}/{url_image}",
            arguments = listOf(
                navArgument("titulo") { type = NavType.StringType },
                navArgument("descripcion") { type = NavType.StringType },
                navArgument("categoriaId") { type = NavType.StringType },
                navArgument("url_image") { type = NavType.StringType }
            )

        ) { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
            val descripcion = backStackEntry.arguments?.getString("descripcion") ?: ""
            val categoriaId = backStackEntry.arguments?.getString("categoriaId") ?: ""
            val url_image = Uri.decode(backStackEntry.arguments?.getString("url_image") ?: "")
            DetalleInfo(
                navController = navController,
                titulo = titulo,
                descripcion = descripcion,
                categoriaId = categoriaId,
                url_image = url_image
            )
        }

        composable(
            route = "servicios_info/{titulo}/{descripcion}/{servicioId}/{url_image}",
            arguments = listOf(
                navArgument("titulo") { type = NavType.StringType },
                navArgument("descripcion") { type = NavType.StringType },
                navArgument("servicioId") { type = NavType.StringType },
                navArgument("url_image") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
            val descripcion = backStackEntry.arguments?.getString("descripcion") ?: ""
            val servicioId = backStackEntry.arguments?.getString("servicioId") ?: ""
            val url_imagen = Uri.decode(backStackEntry.arguments?.getString("url_image") ?: "")
            ServiciosInfo(
                navController = navController,
                titulo = titulo,
                descripcion = descripcion,
                servicioId = servicioId,
                url_imagen = url_imagen
            )
        }

        composable("Juribot") { JuriBotScreen(navController) }

        composable(
            route = "SegundoFormulario/{caseId}",
            arguments = listOf(
                navArgument("caseId") {type = NavType.StringType}
            )
        ) { backStackEntry ->

            val caseId = backStackEntry.arguments?.getString("caseId") ?: ""

            SegundoFormulario(
                navController =  navController,
                caseId = caseId
            )
        }

        composable(
            route = "editarcitaUsuario/{appointmentId}",
            arguments = listOf(
                navArgument("appointmentId") { type = NavType.StringType }
            )
        )
        { backStackEntry ->

            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""

            CancelarOConfirmarCita(
                navController,
                appointmentId = appointmentId
            )
        }


        /*=======================================================================================*/
        // PANTALLAS DE ADMIN
        /*=======================================================================================*/
        composable("pantallainfoadmin") { PantallaInfoAdmin(navController) }

        composable(
            route = "modificar-info/{titulo}/{descripcion}/{categoriaId}/{url_image}",
            arguments = listOf(
                navArgument("titulo") { type = NavType.StringType },
                navArgument("descripcion") { type = NavType.StringType },
                navArgument("categoriaId") { type = NavType.StringType },
                navArgument("url_image") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
            val descripcion = backStackEntry.arguments?.getString("descripcion") ?: ""
            val servicioId = backStackEntry.arguments?.getString("categoriaId") ?: ""
            val url_imagen = Uri.decode(backStackEntry.arguments?.getString("url_image") ?: "")
            ModificarInfoCategoriaAdmin(
                navController = navController,
                id = servicioId,
                titulo = titulo,
                descripcion = descripcion,
                urlimagen = url_imagen
            )
        }


        // PANTALLA PARA EDITAR VIDEO
        composable(
            route = "editar_video/{videoId}",
            arguments = listOf(
                navArgument("videoId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
            EditVideoScreen(
                navController = navController,
                videoId = videoId
            )
        }

        composable(
            route = "modificar_servicios_info/{titulo}/{descripcion}/{servicioId}/{url_image}",
            arguments = listOf(
                navArgument("titulo") { type = NavType.StringType },
                navArgument("descripcion") { type = NavType.StringType },
                navArgument("servicioId") { type = NavType.StringType },
                navArgument("url_image") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
            val descripcion = backStackEntry.arguments?.getString("descripcion") ?: ""
            val servicioId = backStackEntry.arguments?.getString("servicioId") ?: ""
            val url_imagen = Uri.decode(backStackEntry.arguments?.getString("url_image") ?: "")
            ModificarServiciosInfoAdmin(
                navController = navController,
                id = servicioId,
                titulo = titulo,
                descripcion = descripcion,
                urlimagen = url_imagen
            )
        }

        composable("agregar-info-admin") { AgregarInfoAdmin(navController) }
        composable("agregar_servicios_info-admin") { AgregarServiciosInfoAdmin(navController) }
        composable("generalsolicitudadmin") { GeneralSolicitudAdmin(navController) }
        composable(
            route = "actualizarcasos/{case_id}",
            arguments = listOf(
                navArgument("case_id") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val case_id = backStackEntry.arguments?.getString("case_id") ?: ""

            ActualizarCasos(
                navController = navController,
                case_id = case_id
            )
        }
        composable(
            route = "modificarsegundoformulario/{case_id}",
            arguments = listOf(
                navArgument("case_id") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val case_id = backStackEntry.arguments?.getString("case_id") ?: ""

            ModificarSegundoFormulario(
                navController = navController,
                caseId = case_id
            )
        }

        composable("infoclinicaadmin") { PantallaInfoClinicaAdmin(navController) }
        composable(
            route = "editarcitaAdmin/{caseId}/{appointmentId}",
            arguments = listOf(
                navArgument("caseId") { type = NavType.StringType },
                navArgument("appointmentId") { type = NavType.StringType }
            )
        )
        { backStackEntry ->

            val caseId = backStackEntry.arguments?.getString("caseId") ?: ""
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""

            EditarCitaAdmin(
            navController,
            caseId = caseId,
            appointmentId = appointmentId
            )
        }

        composable(
            route = "agendar/{caseId}",
            arguments = listOf(
                navArgument("caseId") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val caseId = backStackEntry.arguments?.getString("caseId") ?: ""

            Agendar(
                navController = navController,
                caseId = caseId
            )
        }
        composable(
            route = "comentar/{caseId}",
            arguments = listOf(
                navArgument("caseId") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val caseId = backStackEntry.arguments?.getString("caseId") ?: ""

            ComentarioScreen(
                navController = navController,
                caseId = caseId
            )
        }

        //PANTALLA DEL JURIBOT PARA ADMIN
        composable("JuriBotAdmin") { JuriBotAdmin(navController) }




        /*=======================================================================================*/
        // PANTALLAS DE ESTUDIANTE
        /*=======================================================================================*/
        //PANTALLA DE INICIO DE ESTUDIANTE
        composable("pantallainfoestudiante") { PantallaInfoEstudiante(navController) }

        //PANTALLA DE HISTORIAL DE SOLICITUDES
        composable("generasolicitudestudiante") { GenerarSolicitudEstudiante(navController) }

        //PANTALLA DE DATOS DE LA CLINICA
        composable("informacionestudiate") { InformacionClinicaEstudiante(navController) }

        //PANTALLA DE VISTA DETALLADA DEL CASO
        composable(
            route = "detallecasoestudiante/{case_id}",
            arguments = listOf(
                navArgument("case_id") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val case_id = backStackEntry.arguments?.getString("case_id") ?: ""

            DetalleCasoEstudiante(
                navController = navController,
                case_id = case_id
            )
        }

        composable(
            route = "editarcitaEstudiante/{caseId}/{appointmentId}",
            arguments = listOf(
                navArgument("caseId") { type = NavType.StringType },
                navArgument("appointmentId") { type = NavType.StringType }
            )
        )
        { backStackEntry ->

            val caseId = backStackEntry.arguments?.getString("caseId") ?: ""
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""

            EditarCitaEstudiante(
                navController,
                caseId = caseId,
                appointmentId = appointmentId
            )
        }

        //AGREGAR CATEGORIA Y SERVICIOS ESTUDIANTES
        composable("agregar-info-estudiante") { AgregarInfoEstudiante(navController) }
        composable("agregar-servicio-estudiante") { AgregarServiciosInfoEstudiante(navController) }
        composable("AgregarEvento") { AgregarEvento(navController) }
        composable("AccionVideo") { AccionVideo(navController) }

        composable(
            route = "comentarestudiante/{caseId}",
            arguments = listOf(
                navArgument("caseId") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val caseId = backStackEntry.arguments?.getString("caseId") ?: ""

            ComentarioScreenEstudiante(
                navController = navController,
                caseId = caseId
            )
        }

        composable(
            route = "agendarestudiante/{caseId}",
            arguments = listOf(
                navArgument("caseId") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val caseId = backStackEntry.arguments?.getString("caseId") ?: ""

            AgendarEstudiante(
                navController = navController,
                caseId = caseId
            )
        }

        composable(
            route = "modificarsegundoformularioestudiante/{case_id}",
            arguments = listOf(
                navArgument("case_id") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val case_id = backStackEntry.arguments?.getString("case_id") ?: ""

            ModificarSegundoFormularioEstudiante(
                navController = navController,
                caseId = case_id
            )
        }



    }
}
