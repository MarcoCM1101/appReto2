package mx.itesm.appreto2


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase de datos Usuario con los campos necesarios.
data class Usuario(val nombre: String, val apellido: String, val edad: Int, val genero: String, val curp: String);

// Clase RegistroUsuarioViewModel que hereda de ViewModel.
// Esta clase se encarga de manejar la lógica para enviar usuarios.
class RegistroUsuarioViewModel : ViewModel() {

    // Método para enviar usuario.
    fun enviarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            // URL del endpoint para enviar usuario.
            val url = "http://54.164.8.30:8080/InsertUsuario";

            // Convierte el objeto Usuario a un objeto JSON.
            val json = Gson().toJson(usuario);

            // Instancia del cliente HTTP para realizar las solicitudes.
            val client = OkHttpClient();

            // Convierte el objeto JSON a un cuerpo de solicitud HTTP.
            val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json);

            // Construye la solicitud HTTP.
            val request = Request.Builder()
                .url(url)
                .post(body)
                .build();

            // Realiza la solicitud HTTP de forma asíncrona.
            client.newCall(request).enqueue(object : Callback {
                // Método que se llama cuando la solicitud falla.
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                }

                // Método que se llama cuando se recibe una respuesta HTTP.
                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        throw IOException("Unexpected code $response");
                    }

                    println(response.body?.string());
                }
            })
        }
    }
}
