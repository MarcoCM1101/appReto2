package mx.itesm.appreto2.View

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import mx.itesm.appreto2.R



/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase Home que hereda de Fragment.
// Esta clase se encarga de manejar la vista principal de la aplicación.
class Home : Fragment() {

    // Método que se llama cuando se crea la vista del fragmento.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla la vista con el layout correspondiente.
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Referencia al WebView y carga la URL del sitio web.
        val webView = view.findViewById<WebView>(R.id.webview)
        webView.loadUrl("https://www.difatizapan.gob.mx/")

        // Referencia al botón y configura un OnClickListener.
        // Cuando se hace clic en el botón, navega al fragmento de registro de usuario.
        val button = view.findViewById<MaterialButton>(R.id.button4)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_usuarioFrag_to_registroUsuario)
        }

        // Referencia al botón de la encuesta y configura un OnClickListener.
        // Cuando se hace clic en el botón, abre la URL de la encuesta en el navegador.
        val surveyButton = view.findViewById<Button>(R.id.button3)
        surveyButton.setOnClickListener {
            val url = "http://54.164.8.30:8080/formulario.html"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        return view
    }
}
