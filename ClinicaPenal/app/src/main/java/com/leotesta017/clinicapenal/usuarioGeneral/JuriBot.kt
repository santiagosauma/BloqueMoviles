import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.TopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class ChatViewModel : ViewModel() {
    var messages by mutableStateOf(listOf<Pair<String, Boolean>>())
        private set

    fun sendMessage(message: String) {
        messages = messages + (message to true)
        viewModelScope.launch {
            val response = getChatbotResponse(message)
            messages = messages + (response to false)
        }
    }

    private suspend fun getChatbotResponse(message: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val json = JSONObject().apply {
                    put("contents", JSONArray().apply {
                        put(JSONObject().apply {
                            put("role", "user")
                            put("parts", JSONArray().apply {
                                put(JSONObject().apply {
                                    put("text", message)
                                })
                            })
                        })
                    })
                }

                val requestBody = json.toString().toRequestBody("application/json".toMediaType())

                val request = Request.Builder()
                    .url("https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent?key=AIzaSyCU-9kLsA2wd4rXZYg9gjk4ypo9ILwVV1s")
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .build()

                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        val jsonResponse = JSONObject(responseBody)
                        val candidates = jsonResponse.getJSONArray("candidates")
                        if (candidates.length() > 0) {
                            val content = candidates.getJSONObject(0)
                                .getJSONObject("content")
                                .getJSONArray("parts")
                                .getJSONObject(0)
                                .getString("text")
                            return@withContext content
                        } else {
                            return@withContext "Error: No candidates returned."
                        }
                    } else {
                        return@withContext "Error: Response body is null."
                    }
                } else {
                    val errorBody = response.body?.string()
                    return@withContext "Error: ${response.code} - ${response.message}. Details: $errorBody"
                }
            } catch (e: Exception) {
                return@withContext "Exception: ${e.message}"
            }
        }
    }
}

@Composable
fun JuriBotScreen(navController: NavController?, chatViewModel: ChatViewModel = viewModel()) {
    val messages = chatViewModel.messages
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            TopBar()

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                IconButton(onClick = {
                    navController?.navigate("pantallainfocategoriasgeneral")
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Regresar"
                    )
                }
                Text(
                    text = "JuriBot",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                state = listState
            ) {
                items(messages) { (message, isUserMessage) ->
                    MessageBubble(
                        message = message,
                        isUserMessage = isUserMessage
                    )
                }
            }

            MessageInputBar(onSendMessage = { message ->
                chatViewModel.sendMessage(message)
            })
        }

        BarraNav(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun MessageBubble(message: String, isUserMessage: Boolean) {
    val backgroundColor = if (isUserMessage) Color.LightGray else Color.Blue
    val textColor = if (isUserMessage) Color.Black else Color.White
    val alignment = if (isUserMessage) Alignment.CenterEnd else Alignment.CenterStart
    val cornerShape = if (isUserMessage) {
        androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
    } else {
        androidx.compose.foundation.shape.RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomEnd = 16.dp,
            bottomStart = 4.dp
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor, shape = cornerShape)
                .padding(16.dp)
                .widthIn(min = 100.dp, max = 300.dp)
        ) {
            Text(
                text = message,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Visible
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInputBar(onSendMessage: (String) -> Unit) {
    val inputText = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = inputText.value,
            onValueChange = { inputText.value = it },
            placeholder = { Text("Enviar Mensaje...") },
            trailingIcon = {
                IconButton(onClick = {
                    if (inputText.value.isNotEmpty()) {
                        onSendMessage(inputText.value)
                        inputText.value = ""
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Enviar mensaje"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, Color.Black, shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp))
                .background(Color.White, shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun JuriBotScreenPreview() {
    JuriBotScreen(navController = null)
}
