package mx.itesm.appreto2.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.itesm.appreto2.R
import mx.itesm.appreto2.databinding.FragmentUsuarioBinding


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase UsuarioFrag que hereda de Fragment.
// Esta clase se encarga de manejar la vista del usuario.
class UsuarioFrag : Fragment() {

    // Instancia del binding para este fragmento.
    private lateinit var binding : FragmentUsuarioBinding

    // Método que se llama cuando se crea la vista del fragmento.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla la vista con el layout correspondiente.
        binding = FragmentUsuarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Método que se llama cuando la vista del fragmento ha sido creada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Reemplaza el fragmento actual con el fragmento de inicio.
        replaceFragment(Home())

        // Configura el comportamiento de la barra de navegación inferior.
        binding.bottomNavigationView.setOnItemSelectedListener {

            // Dependiendo del ítem seleccionado, reemplaza el fragmento actual con el correspondiente.
            when (it.itemId) {
                R.id.home -> replaceFragment(Home())
                R.id.announcement -> replaceFragment(Anuncios())
                R.id.map -> replaceFragment(Map())
                R.id.qr -> replaceFragment(Qr())

                else -> {

                }
            }
            true
        }
    }

    // Método para reemplazar el fragmento actual con uno nuevo.
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}
