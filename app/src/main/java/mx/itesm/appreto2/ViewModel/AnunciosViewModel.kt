package mx.itesm.appreto2.ViewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.io.IOException


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase AnunciosViewModel.
// Esta clase se encarga de manejar la lógica para obtener los anuncios.
class AnunciosViewModel {

    // Instancia del cliente HTTP para realizar las solicitudes.
    private val client = OkHttpClient()

    // Método suspendido para obtener los anuncios.
    // Este método realiza una solicitud HTTP para obtener los anuncios y devuelve un JSONArray con los datos de los anuncios.
    suspend fun fetchAnuncios(): JSONArray {
        // Construye la solicitud HTTP.
        val request = Request.Builder()
            .url("http://54.164.8.30:8080/anunciosActivos")
            .build()

        // Realiza la solicitud en un contexto de E/S y procesa la respuesta.
        return withContext(Dispatchers.IO) {
            client.newCall(request).execute().use { response ->
                // Si la respuesta no es exitosa, lanza una excepción.
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                // Devuelve los datos de los anuncios como un JSONArray.
                JSONArray(response.body!!.string())
            }
        }
    }
}
