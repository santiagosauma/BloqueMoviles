package com.leotesta017.clinicapenal.view.usuarioEstudiante

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.R
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.EstudiantesBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar

@Composable
fun InformacionClinicaEstudiante(navController: NavController?) {
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
                Spacer(modifier = Modifier.height(20.dp))
                SeccionNosotros()
                Spacer(modifier = Modifier.height(20.dp))
                SeccionHorarios()
                Spacer(modifier = Modifier.height(20.dp))
                SeccionDirecciones()
                Spacer(modifier = Modifier.height(20.dp))
                SeccionContacto()
                Spacer(modifier = Modifier.height(20.dp))
                RedesSociales()
                Spacer(modifier = Modifier.height(20.dp))
                Calendarios()
            }
        }

        EstudiantesBarraNav(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun SeccionNosotros() {
    Box(
        modifier = Modifier
            .width(350.dp)
            .background(Color.LightGray)
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Nosotros", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Apoyamos a la comunidad con representación legal en procesos judiciales, asegurando que todos tengan acceso a la justicia.",
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun SeccionHorarios() {
    Box(
        modifier = Modifier
            .width(350.dp)
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Horarios", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Lunes a Viernes de 9:00 AM a 4:00 PM",
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun SeccionDirecciones() {
    Box(
        modifier = Modifier
            .width(350.dp)
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Direcciones", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("•")
                    }
                    append(" Av. Eugenio Garza Sada 2501 Sur, Monterrey, N.L.")
                },
                fontSize = 12.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("•")
                    }
                    append(" Rufino Tamayo y Eugenio, Av. Eugenio Garza Lagüera, San Pedro Garza García, N.L.")
                },
                fontSize = 12.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun SeccionContacto() {
    Box(
        modifier = Modifier
            .width(350.dp)
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Contacto", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("• Teléfono:")
                    }
                    append(" +52 0123456789")
                },
                fontSize = 12.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("• Correo:")
                    }
                    append(" bufetec@tec.mx ")
                },
                fontSize = 12.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun RedesSociales() {
    Box(
        modifier = Modifier
            .width(350.dp)
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Redes Sociales", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .width(350.dp)
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "Facebook",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { }
                )
                Image(
                    painter = painterResource(id = R.drawable.logox),
                    contentDescription = "X",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { }
                )
                Image(
                    painter = painterResource(id = R.drawable.youtube),
                    contentDescription = "YouTube",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { }
                )
            }
        }
    }
}

@Composable
fun Calendarios() {
    Box(
        modifier = Modifier
            .width(350.dp)
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Calendario Eventos", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("• Lunes 12 de Agosto:")
                    }
                    append(" Conferencia Derecho Vehicular")
                },
                fontSize = 12.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("• Viernes 18 de Agosto:")
                    }
                    append(" Taller Documentación Legal Civil")
                },
                fontSize = 12.sp,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInformacionClinicaEstudiante() {
    InformacionClinicaEstudiante(navController = null)
}
