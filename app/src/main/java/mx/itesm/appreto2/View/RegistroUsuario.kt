package mx.itesm.appreto2.View

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.URLSpan
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.itesm.appreto2.R
import mx.itesm.appreto2.RegistroUsuarioViewModel
import mx.itesm.appreto2.Usuario


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase RegistroUsuario que hereda de Fragment.
// Esta clase se encarga de manejar la vista para el registro de usuarios.
class RegistroUsuario : Fragment(R.layout.fragment_registro_usuario) {

    // Instancia del ViewModel para el registro de usuarios.
    private lateinit var viewModel: RegistroUsuarioViewModel

    // Método que se llama cuando la vista del fragmento ha sido creada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialización del ViewModel.
        viewModel = ViewModelProvider(this).get(RegistroUsuarioViewModel::class.java)

        // Referencias a los elementos de la vista.
        val spinner: Spinner = view.findViewById(R.id.spinner)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrayOf("Género","Femenino", "Masculino", "Otro")
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val nombreInput = view.findViewById<EditText>(R.id.nombre_input)
        val apellidoInput = view.findViewById<EditText>(R.id.apellido_input)
        val edadInput = view.findViewById<EditText>(R.id.edad_input)
        val curpInput = view.findViewById<EditText>(R.id.curp_input)
        val confirmarButton = view.findViewById<Button>(R.id.button2)
        val checkBoxTerminos = view.findViewById<CheckBox>(R.id.cbTerminosCondiciones2)

        // Configuración del botón para confirmar el registro.
        confirmarButton.setOnClickListener {
            if (checkBoxTerminos.isChecked) {
                // Obtiene los datos ingresados por el usuario.
                val nombre = nombreInput.text.toString()
                val apellido = apellidoInput.text.toString()
                val edad = edadInput.text.toString().toInt()
                val genero = spinner.selectedItem.toString()
                val curp = curpInput.text.toString()

                if (genero != "Género") {
                    if (curp.length == 18) {
                        // Crea un nuevo usuario con los datos ingresados y solicita al ViewModel que envíe el usuario.
                        val usuario = Usuario(nombre, apellido, edad, genero, curp)
                        viewModel.enviarUsuario(usuario)

                        // Limpia los campos después de enviar la información del usuario.
                        nombreInput.setText("")
                        apellidoInput.setText("")
                        edadInput.setText("")
                        curpInput.setText("")
                        spinner.setSelection(0)
                        checkBoxTerminos.setChecked(false)

                        // Muestra un mensaje de éxito al usuario.
                        AlertDialog.Builder(requireContext())
                            .setTitle("Registro de usuario")
                            .setMessage("USUARIO REGISTRADO CON ÉXITO")
                            .setPositiveButton(
                                android.R.string.ok,
                                DialogInterface.OnClickListener { dialog, which ->
                                    dialog.dismiss()
                                })
                            .show()
                    } else {
                        Toast.makeText(requireContext(), "El CURP no es válido, debe tener 18 dígitos.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Debes aceptar los términos y condiciones.", Toast.LENGTH_SHORT).show()
            }
        }

        // Configura el TextView para mostrar un enlace a la página de consulta de CURP.
        val textView: TextView = view.findViewById(R.id.textView11)
        val spannableString = SpannableString("¿No conoces tu CURP? Obténlo aquí")
        spannableString.setSpan(
            URLSpan(""),
            0,
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = spannableString
        textView.setOnClickListener {
            // Abre la página de consulta de CURP en el navegador cuando se hace clic en el TextView.
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://consultas.curp.gob.mx/CurpSP/"))
            startActivity(intent)
        }

        // Configura el TextView para mostrar un enlace a los términos y condiciones.
        val tvTerminosCondiciones2: TextView = view.findViewById(R.id.tvTerminosCondiciones2)
        val spannableStringTerminos2 = SpannableString("Consultar Términos y Condiciones")
        spannableStringTerminos2.setSpan(
            URLSpan("http://54.164.8.30:8080/terminosCondiciones.html"),
            0,
            spannableStringTerminos2.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvTerminosCondiciones2.text = spannableStringTerminos2

        // Abre la página de términos y condiciones en el navegador cuando se hace clic en el TextView.
        tvTerminosCondiciones2.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://54.164.8.30:8080/terminosCondiciones.html"))
            startActivity(intent)
        }
    }
}
