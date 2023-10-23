package mx.itesm.appreto2.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.itesm.appreto2.Model.MyApp
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException



/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase EncargadoSettingsViewModel que hereda de ViewModel.
// Esta clase se encarga de manejar la lógica para las configuraciones del encargado.
class EncargadoSettingsViewModel : ViewModel() {

    // Instancia del cliente HTTP para realizar las solicitudes.
    private val client = OkHttpClient()

    // Método para actualizar el estado.
    fun updateStatus(newStatus: String) {
        // Obtiene el id del comedor.
        val idComedor2 = MyApp.idComedor

        // Crea el JSON para la solicitud.
        val json = "{\"newStatus\":\"$newStatus\"}"

        // Crea el cuerpo de la solicitud con el JSON.
        val body = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        // Construye la solicitud HTTP.
        val request = Request.Builder()
            .url("http://54.164.8.30:8080/actualizarEstatusComedor/$idComedor2")
            .put(body)
            .build()

        // Realiza la solicitud HTTP de forma asíncrona.
        CoroutineScope(Dispatchers.IO).launch {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code ${response.code}")

                // Imprime el cuerpo de la respuesta.
                Log.d("API Response", response.body!!.string())
            }
        }
    }

    // Método para indicar que llegó el inventario.
    fun llegoInventario() {
        // Obtiene el id del comedor.
        val idComedor = MyApp.idComedor

        // Crea el JSON para la solicitud.
        val json = "{\"idComedor\": $idComedor}"

        // Crea el cuerpo de la solicitud con el JSON.
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = json.toRequestBody(mediaType)

        // Construye la solicitud HTTP.
        val request = Request.Builder()
            .url("http://54.164.8.30:8080/LlegoInventario")
            .post(body)
            .build()

        // Realiza la solicitud HTTP de forma asíncrona.
        CoroutineScope(Dispatchers.IO).launch {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    // Manejar una respuesta no exitosa aquí
                    Log.e("API Response", "Unexpected code ${response.code}")
                } else {
                    // Manejar una respuesta exitosa
                    Log.d("API Response", response.body!!.string())
                }
            }
        }
    }
}
