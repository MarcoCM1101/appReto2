package mx.itesm.appreto2.ViewModel

import android.app.Activity
import android.util.Log
import mx.itesm.appreto2.Model.User
import okhttp3.*
import org.json.JSONArray
import java.io.IOException


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase EncargadoInicioViewModel que hereda de ViewModel.
// Esta clase se encarga de manejar la lógica para obtener los usuarios.
class EncargadoInicioViewModel(private val activity: Activity) {

    // Instancia del cliente HTTP para realizar las solicitudes.
    private val client = OkHttpClient()

    // URL del endpoint para obtener los usuarios.
    private val url = "http://54.164.8.30:8080/usuarios"

    // Método para obtener los usuarios.
    fun getUsers(callback: (ArrayList<User>) -> Unit) {
        // Construye la solicitud HTTP.
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        // Realiza la solicitud HTTP de forma asíncrona.
        client.newCall(request).enqueue(object : Callback {
            // Método que se llama cuando la solicitud falla.
            override fun onFailure(call: Call, e: IOException) {
                Log.e("EncargadoInicio", "Error al hacer la petición", e)
            }

            // Método que se llama cuando se recibe una respuesta HTTP.
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    Log.e("EncargadoInicio", "Respuesta no exitosa: ${response.code}")
                }

                // Obtiene el cuerpo de la respuesta como una cadena.
                val responseBody = response.body?.string()
                Log.d("EncargadoInicio", "Respuesta recibida: $responseBody")

                if (responseBody?.startsWith("[") == true && responseBody.endsWith("]")) {
                    // Si el cuerpo de la respuesta es un JSON válido, lo procesa y crea una lista de usuarios.
                    val jsonArray = JSONArray(responseBody)

                    val userList = ArrayList<User>()
                    for (i in 0 until jsonArray.length()) {
                        val userJson = jsonArray.getJSONObject(i)
                        val user = User(
                            idUsuario = userJson.getInt("idUsuario"),
                            nombre = userJson.getString("nombre"),
                            apellido = userJson.getString("apellido"),
                            curp = userJson.getString("curp")
                        )
                        userList.add(user)
                    }

                    // Llama al callback con la lista de usuarios.
                    callback(userList)
                } else {
                    Log.e("EncargadoInicio", "La respuesta de la API no es un JSON válido")
                }
            }
        })
    }
}
