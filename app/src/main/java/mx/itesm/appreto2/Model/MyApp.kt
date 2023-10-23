package mx.itesm.appreto2.Model

import android.app.Application


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// MyApp es una clase que hereda de Application.
// Se utiliza para mantener variables globales.
class MyApp : Application() {

    // Objeto compañero que contiene las variables estáticas.
    companion object {
        // idEncargado: Variable para almacenar el ID del encargado.
        var idEncargado: Int = 0

        // idComedor: Variable para almacenar el ID del comedor.
        var idComedor: Int = 0

        // idUsuario: Variable para almacenar el ID del usuario.
        var idUsuario: Int = 0
    }

    // onCreate() se llama cuando se crea la aplicación.
    override fun onCreate() {
        super.onCreate()
        // Aquí puedes inicializar tus componentes
    }
}

