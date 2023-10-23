package mx.itesm.appreto2.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import mx.itesm.appreto2.R
import mx.itesm.appreto2.databinding.FragmentPantallaPrincipalBinding


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase pantallaPrincipalFrag que hereda de Fragment.
// Esta clase se encarga de manejar la vista principal de la aplicación.
class pantallaPrincipalFrag : Fragment(R.layout.fragment_pantalla_principal) {

    // Instancia del binding para este fragmento.
    private lateinit var binding: FragmentPantallaPrincipalBinding

    // Método que se llama cuando la vista del fragmento ha sido creada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el binding.
        binding = FragmentPantallaPrincipalBinding.bind(view)

        // Registra los eventos.
        registrarEventos()
    }

    // Método para registrar los observables.
    private fun registrarObservables() {
    }

    // Método para registrar los eventos.
    private fun registrarEventos() {
        // Configura el evento onClick del botón para navegar al fragmento del encargado.
        binding.btn.setOnClickListener {
            findNavController().navigate(R.id.action_pantallaPrincipalFrag_to_encargadoFrag)
        }

        // Configura el evento onClick del botón del usuario para navegar al fragmento del usuario.
        binding.btnUsuario.setOnClickListener {
            findNavController().navigate(R.id.action_pantallaPrincipalFrag_to_usuarioFrag)
        }
    }
}
