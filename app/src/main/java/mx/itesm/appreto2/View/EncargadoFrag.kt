package mx.itesm.appreto2.View

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import mx.itesm.appreto2.ViewModel.EncargadoViewModel
import mx.itesm.appreto2.R


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase EncargadoFrag que hereda de Fragment.
// Esta clase se encarga de manejar la vista para el encargado.
class EncargadoFrag : Fragment(R.layout.fragment_encargado) {

    // Instancia del ViewModel para el encargado.
    private lateinit var viewModel: EncargadoViewModel

    // Método que se llama cuando la vista del fragmento ha sido creada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialización del ViewModel.
        viewModel = EncargadoViewModel(requireActivity())

        // Referencias a los campos de entrada y al botón de encargado.
        val btnEncargado = view.findViewById<Button>(R.id.button2)
        val usuarioInput = view.findViewById<TextInputEditText>(R.id.usuario_input)
        val contrasenaInput = view.findViewById<TextInputEditText>(R.id.contrasena_input)

        // Configuración del tipo de entrada para la contraseña.
        contrasenaInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD

        // Configuración del botón de encargado.
        btnEncargado.setOnClickListener {
            // Obtiene los datos ingresados por el usuario.
            val usuario = usuarioInput.text.toString()
            val contrasena = contrasenaInput.text.toString()

            // Intenta iniciar sesión con los datos ingresados.
            viewModel.login(usuario, contrasena) { success ->
                activity?.runOnUiThread {
                    if (success) {
                        // Si el inicio de sesión es exitoso, navega a la vista de inicio del encargado.
                        findNavController().navigate(R.id.action_EncargadoFrag_to_encargadoHome)
                    } else {
                        // Si el inicio de sesión falla, muestra un mensaje al usuario.
                        Toast.makeText(activity, "Usuario o Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
