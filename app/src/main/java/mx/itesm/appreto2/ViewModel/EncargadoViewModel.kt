package mx.itesm.appreto2.ViewModel

import android.app.Activity
import android.util.Log
import android.widget.Toast
import mx.itesm.appreto2.Model.MyApp
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase EncargadoViewModel que hereda de ViewModel.
// Esta clase se encarga de manejar la lógica para el inicio de sesión del encargado.
class EncargadoViewModel(private val activity: Activity) {

    // Instancia del cliente HTTP para realizar las solicitudes.
    private val client = OkHttpClient();

    // Método para iniciar sesión.
    fun login(usuario: String, contrasena: String, callback: (Boolean) -> Unit) {
        // Registra los datos enviados a la API.
        Log.d("EncargadoFrag", "Datos enviados a la API: telefono=$usuario, contrasena=$contrasena");

        // Crea el cuerpo de la solicitud con los datos del usuario y la contraseña.
        val requestBody = FormBody.Builder()
            .add("telefono", usuario)
            .add("contrasena", contrasena)
            .build();

        // URL del endpoint para iniciar sesión.
        val url = "http://54.164.8.30:8080/loginEncargado/$usuario/$contrasena";
        Log.d("EncargadoFrag", "URL de la API: $url");

        // Construye la solicitud HTTP.
        val request = Request.Builder()
            .url(url)
            .get()
            .build();

        // Realiza la solicitud HTTP de forma asíncrona.
        client.newCall(request).enqueue(object : Callback {
            // Método que se llama cuando la solicitud falla.
            override fun onFailure(call: Call, e: IOException) {
                activity.runOnUiThread {
                    Toast.makeText(activity, "Usuario o Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
                callback(false);
            }

            // Método que se llama cuando se recibe una respuesta HTTP.
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    Log.e("EncargadoFrag", "Respuesta no exitosa: ${response.code}");
                    activity.runOnUiThread {
                        Toast.makeText(activity, "Usuario o Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                    callback(false);
                }

                val responseBody = response.body?.string();
                Log.d("EncargadoFrag", "Respuesta recibida: $responseBody");

                if (responseBody?.startsWith("{") == true && responseBody.endsWith("}")) {
                    val json = JSONObject(responseBody);
                    val login = json.getString("login");

                    if (response.isSuccessful && login == "TRUE") {
                        MyApp.idEncargado = json.getInt("idEncargado");
                        MyApp.idComedor = json.getInt("idComedor");
                        callback(true);
                    } else {
                        Log.e("EncargadoFrag", "Error en la respuesta de la API");
                        activity.runOnUiThread {
                            Toast.makeText(activity, "Usuario o Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                        callback(false);
                    }
                } else {
                    Log.e("EncargadoFrag", "La respuesta de la API no es un JSON válido");
                    callback(false);
                }
            }
        })
    }
}
