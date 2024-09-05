package com.leotesta017.clinicapenal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pantallainformacion.PantallaInfoClinica
import com.leotesta017.clinicapenal.loginRegister.*
import com.leotesta017.clinicapenal.solicitud.GeneralSolicitud
import com.leotesta017.clinicapenal.usuarioColaborador.ActualizarCasos
import com.leotesta017.clinicapenal.usuarioColaborador.Agendar
import com.leotesta017.clinicapenal.usuarioColaborador.AgregarInfoAdmin
import com.leotesta017.clinicapenal.usuarioColaborador.AgregarServiciosInfoAdmin
import com.leotesta017.clinicapenal.usuarioColaborador.ComentarioScreen
import com.leotesta017.clinicapenal.usuarioColaborador.GeneralSolicitudAdmin
import com.leotesta017.clinicapenal.usuarioColaborador.JuriBotAdmin
import com.leotesta017.clinicapenal.usuarioColaborador.ModificarInfoAdmin
import com.leotesta017.clinicapenal.usuarioColaborador.ModificarServiciosAdmin
import com.leotesta017.clinicapenal.usuarioColaborador.PantallaInfoAdmin
import com.leotesta017.clinicapenal.usuarioColaborador.PantallaInfoClinicaAdmin
import com.leotesta017.clinicapenal.usuarioEstudiante.DetalleCasoEstudiante
import com.leotesta017.clinicapenal.usuarioEstudiante.GenerarSolicitudEstudiante
import com.leotesta017.clinicapenal.usuarioEstudiante.InformacionClinicaEstudiante
import com.leotesta017.clinicapenal.usuarioEstudiante.PantallaInfoEstudiante
import com.leotesta017.clinicapenal.usuarioGeneral.DetalleInfo
import com.leotesta017.clinicapenal.usuarioGeneral.PantallaInfoCategorias
import com.leotesta017.clinicapenal.usuarioGeneral.ReviewComentarios
import com.leotesta017.clinicapenal.usuarioGeneral.SegundoFormulario
import com.leotesta017.clinicapenal.usuarioGeneral.ServiciosInfo
import com.leotesta017.clinicapenal.usuarioGeneral.Solicitud
import com.leotesta017.clinicapenal.usuarioGeneral.JuriBotScreen as JuriBot

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
        composable("detalle_info") { DetalleInfo(navController) }
        composable("crearsolicitud") { Solicitud(navController) }
        composable("servicios_info") { ServiciosInfo(navController)}
        composable("Juribot") { JuriBot(navController)}
        composable("SegundoFormulario") { SegundoFormulario(navController)}

        //Pantallas Usuario Admin
        composable("pantallainfoadmin") { PantallaInfoAdmin(navController)}
        composable("modificar-info") { ModificarInfoAdmin(navController)}
        composable("modificar_servicios_info") { ModificarServiciosAdmin(navController)}
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

