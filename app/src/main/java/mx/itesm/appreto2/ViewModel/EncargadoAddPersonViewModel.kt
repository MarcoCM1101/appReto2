package mx.itesm.appreto2.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.itesm.appreto2.Model.MyApp
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase de datos para representar a un usuario.
data class Usuario2(val nombre: String, val apellido: String, val edad: Int, val genero: String, val curp: String)

// Clase EncargadoAddPersonViewModel que hereda de ViewModel.
// Esta clase se encarga de manejar la lógica para agregar una persona.
class EncargadoAddPersonViewModel : ViewModel() {

    // Instancia del cliente HTTP para realizar las solicitudes.
    private val client = OkHttpClient()

    // Método para enviar un usuario.
    fun enviarUsuario(usuario: Usuario2) {
        // URL del endpoint para insertar un usuario.
        val url = "http://54.164.8.30:8080/InsertUsuario"

        // Convierte el usuario a JSON.
        val json = Gson().toJson(usuario)

        // Crea el cuerpo de la solicitud con el JSON del usuario.
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        // Construye la solicitud HTTP.
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        // Realiza la solicitud HTTP de forma asíncrona.
        client.newCall(request).enqueue(object : Callback {
            // Método que se llama cuando la solicitud falla.
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            // Método que se llama cuando se recibe una respuesta HTTP.
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }

                // Imprime el cuerpo de la respuesta.
                println(response.body?.string())
            }
        })
    }
}
