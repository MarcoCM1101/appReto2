package mx.itesm.appreto2.View

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.itesm.appreto2.R
import mx.itesm.appreto2.ViewModel.AnunciosViewModel


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase Anuncios que hereda de Fragment.
// Esta clase se encarga de manejar la vista de los anuncios.
class Anuncios : Fragment(R.layout.fragment_anuncios) {

    // Instancia del ViewModel para los anuncios.
    private val viewModel = AnunciosViewModel()

    // Método que se llama cuando la vista del fragmento ha sido creada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Botón para abrir la página de Facebook.
        val facebookButton = view.findViewById<ImageButton>(R.id.imgBtnFacebook)
        facebookButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/AtizapanDIF/"))
            startActivity(intent)
        }

        // Botón para abrir la página de Twitter.
        val twitterButton = view.findViewById<ImageButton>(R.id.imgBtnTwitter)
        twitterButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/difatizapanz?lang=es"))
            startActivity(intent)
        }

        // Botón para abrir la página de Instagram.
        val instagramButton = view.findViewById<ImageButton>(R.id.imgBtnInstagram)
        instagramButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/difatizapanz/?hl=es-la"))
            startActivity(intent)
        }

        // ListView para mostrar los anuncios.
        val listViewAnuncios = view.findViewById<ListView>(R.id.listViewAnuncios)

        // Lanzamos una corrutina en el hilo principal.
        CoroutineScope(Dispatchers.Main).launch {
            // Obtenemos los anuncios del ViewModel.
            val anuncios = viewModel.fetchAnuncios()
            val listItems = ArrayList<String>()
            for (i in 0 until anuncios.length()) {
                val anuncio = anuncios.getJSONObject(i)
                listItems.add(anuncio.getString("titulo") + "\n" + anuncio.getString("descripcion"))
            }
            // Creamos un adaptador y lo asignamos al ListView.
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listItems)
            listViewAnuncios.adapter = adapter
        }
    }
}
