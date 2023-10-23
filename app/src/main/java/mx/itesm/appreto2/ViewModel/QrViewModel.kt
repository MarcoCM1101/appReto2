package mx.itesm.appreto2.ViewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import mx.itesm.appreto2.Model.MyApp
import okhttp3.*
import java.io.IOException


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase de datos Usuario3 con los campos necesarios.
data class Usuario3(val nombre: String, val apellido: String, val edad: Int, val genero: String, val curp: String, val idUsuario: Int);

// Clase QrViewModel que hereda de ViewModel.
// Esta clase se encarga de manejar la lógica para obtener usuarios.
class QrViewModel : ViewModel() {
    // LiveData para el usuario.
    val usuarioLiveData: MutableLiveData<Usuario3?> = MutableLiveData();
    // LiveData para el estado de éxito.
    val isSuccessful: MutableLiveData<Boolean> = MutableLiveData();

    // Método para obtener usuario.
    fun obtenerUsuario(curp: String) {
        viewModelScope.launch {
            // URL del endpoint para obtener usuario.
            val url = "http://54.164.8.30:8080/usuarioCurp/$curp";
            val client = OkHttpClient();
            val request = Request.Builder().url(url).build();

            // Realiza la solicitud HTTP de forma asíncrona.
            client.newCall(request).enqueue(object : Callback {
                // Método que se llama cuando la solicitud falla.
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    isSuccessful.postValue(false);
                }

                // Método que se llama cuando se recibe una respuesta HTTP.
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val json = response.body?.string();
                        val usuario3 = Gson().fromJson(json, Usuario3::class.java);
                        MyApp.idUsuario = usuario3.idUsuario;
                        usuarioLiveData.postValue(usuario3);
                        isSuccessful.postValue(true);
                    } else {
                        isSuccessful.postValue(false);
                    }
                }
            })
        }
    }
}
