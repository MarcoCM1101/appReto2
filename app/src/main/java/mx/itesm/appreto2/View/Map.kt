package mx.itesm.appreto2.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import mx.itesm.appreto2.R


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase Map que hereda de Fragment.
// Esta clase se encarga de manejar la vista del mapa.
class Map : Fragment() {

    // Método que se llama cuando se crea la vista del fragmento.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla la vista con el layout correspondiente.
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        // Referencia al WebView y configura sus ajustes.
        val webView = view.findViewById<WebView>(R.id.mapaweb)
        webView.settings.javaScriptEnabled = true  // Habilita JavaScript.
        webView.settings.domStorageEnabled = true  // Habilita el almacenamiento DOM.

        // Carga la URL del mapa en el WebView.
        webView.loadUrl("https://www.google.com/maps/d/viewer?mid=16p4XUJ3OiJqezpHleTAKGHy4Ti_8rYc&ll=19.575418665693352%2C-99.24592264703536&z=13")

        return view
    }
}
