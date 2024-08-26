package com.example.pantallainformacin

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Pantalla() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Clínica Penal", fontSize = 38.sp, fontWeight = FontWeight.ExtraBold, color = Color.Blue)

        Spacer(modifier = Modifier.height(15.dp))

        Box(
            modifier = Modifier
                .height(150.dp)
                .width(300.dp)
                .background(Color.LightGray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),  // Se agregó padding para evitar que el texto toque los bordes
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Nosotros", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)

                Spacer(modifier = Modifier.height(10.dp))

                // Este texto ahora estará centrado
                Text(
                    text = "Apoyamos a la comunidad con representación legal en procesos judiciales, asegurando que todos tengan acceso a la justicia.",
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Se asegura que el texto esté centrado horizontalmente
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Box(
            modifier = Modifier
                .height(75.dp)
                .width(300.dp)
                .background(Color.LightGray)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Horarios", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Lunes a Viernes de 9:00 AM a 4:00 PM",
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Se asegura que el texto esté centrado horizontalmente
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Box(
            modifier = Modifier
                .height(90.dp)
                .width(300.dp)
                .background(Color.LightGray)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
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
                        append("  Av. Eugenio Garza Sada 2501 Sur, Monterrey, N.L.")
                    },
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Se asegura que el texto esté centrado horizontalmente
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(" •")
                        }
                        append(" Rufino Tamayo y Eugenio, Av. Eugenio Garza Lagüera, San Pedro Garza García, N.L.")
                    },
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Se asegura que el texto esté centrado horizontalmente
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .height(70.dp)
                .width(300.dp)
                .background(Color.LightGray)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Contacto", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("• Teléfono:")
                        }
                        append(" +52 0123456789")
                    },
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(" • Correo:")
                        }
                        append(" bufetec@tec.mx ")
                    },
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }


        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .height(85.dp)
                .width(300.dp)
                .background(Color.LightGray)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Redes Sociales", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)

                Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                        Image(painter = painterResource(id = R.drawable.logofacebook),
                            contentDescription = "Facebook",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable { })

                        Image(painter = painterResource(id = R.drawable.logox),
                            contentDescription = "X",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable { })

                        Image(painter = painterResource(id = R.drawable.logoyoutube),
                            contentDescription = "YouTube",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable { })
                    }

            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .height(90.dp)
                .width(300.dp)
                .background(Color.LightGray)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Calendario Eventos", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("• Lunes 12 de Agosto:")
                        }
                        append("  Conferencia Derecho Vehicular")
                    },
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("• Viernes 18 de Agosto:")
                        }
                        append(" Taller Documentación Legal Civil")
                    },
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

    }
}
