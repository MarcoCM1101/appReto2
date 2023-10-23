package mx.itesm.appreto2.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase PedidosViewModel que hereda de ViewModel.
// Esta clase se encarga de manejar la lógica para enviar usuarios.
class PedidosViewModel : ViewModel() {

    // Instancia del cliente HTTP para realizar las solicitudes.
    private val client = OkHttpClient();

    // Método para enviar usuario.
    fun enviarUsuario(idUsuario: Int, idComedor: Int, donativo: Int) {
        // URL del endpoint para enviar usuario.
        val url = "http://54.164.8.30:8080/InsertUsuarioComedor";

        // Crea un objeto JSON con los datos del usuario.
        val json = JSONObject();
        json.put("idUsuario", idUsuario);
        json.put("idComedor", idComedor);
        json.put("donativo", donativo);

        // Convierte el objeto JSON a un cuerpo de solicitud HTTP.
        val body = json.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull());

        // Construye la solicitud HTTP.
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build();

        // Realiza la solicitud HTTP de forma asíncrona.
        client.newCall(request).enqueue(object : Callback {
            // Método que se llama cuando la solicitud falla.
            override fun onFailure(call: Call, e: IOException) {
                Log.e("EncargadoInicio", "Error al hacer la petición", e);
            }

            // Método que se llama cuando se recibe una respuesta HTTP.
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    Log.e("EncargadoInicio", "Respuesta no exitosa: ${response.code}");
                } else {
                    Log.d("EncargadoInicio", "Usuario añadido al comedor con éxito");
                }
            }
        });
    }
}
