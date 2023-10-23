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
import com.google.gson.Gson
import mx.itesm.appreto2.R
import mx.itesm.appreto2.ViewModel.EncargadoAddPersonViewModel
import mx.itesm.appreto2.ViewModel.Usuario2

/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase encargadoAddPerson que hereda de Fragment.
// Esta clase se encarga de manejar la vista para agregar una persona.
class encargadoAddPerson : Fragment(R.layout.fragment_encargado_add_person) {

    // Instancia del ViewModel para agregar una persona.
    private val viewModel = EncargadoAddPersonViewModel()

    // Método que se llama cuando la vista del fragmento ha sido creada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuración del Spinner para seleccionar el género.
        val spinner: Spinner = view.findViewById(R.id.spinner4)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrayOf("Género", "Femenino", "Masculino", "Otro")
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Referencias a los campos de entrada y al botón de agregar.
        val nombreInput = view.findViewById<EditText>(R.id.nombre_input2)
        val apellidoInput = view.findViewById<EditText>(R.id.apellido_input2)
        val edadInput = view.findViewById<EditText>(R.id.edad_input2)
        val curpInput = view.findViewById<EditText>(R.id.curp_input2)
        val agregarButton = view.findViewById<Button>(R.id.button5)
        val cbTerminosCondiciones = view.findViewById<CheckBox>(R.id.cbTerminosCondiciones)

        // Configuración del botón de agregar.
        agregarButton.setOnClickListener {
            // Verifica si los términos y condiciones están aceptados.
            if (cbTerminosCondiciones.isChecked) {
                // Obtiene los datos ingresados por el usuario.
                val nombre = nombreInput.text.toString()
                val apellido = apellidoInput.text.toString()
                val edad = edadInput.text.toString().toInt()
                val genero = spinner.selectedItem.toString()
                val curp = curpInput.text.toString()

                // Verifica si el género es válido y si el CURP tiene la longitud correcta.
                if (genero != "Género") {
                    if (curp.length == 18) {
                        // Crea un nuevo usuario y lo envía al ViewModel.
                        val usuario2 = Usuario2(nombre, apellido, edad, genero, curp)
                        viewModel.enviarUsuario(usuario2)

                        // Limpia los campos después de enviar la información del usuario.
                        nombreInput.setText("")
                        apellidoInput.setText("")
                        edadInput.setText("")
                        curpInput.setText("")
                        spinner.setSelection(0)
                        cbTerminosCondiciones.setChecked(false)

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

        // Configuración del TextView para obtener el CURP.
        val textView: TextView = view.findViewById(R.id.textView16)
        val spannableString = SpannableString("¿No conoces tu CURP? Obténlo aquí")
        spannableString.setSpan(
            URLSpan(""),
            0,
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = spannableString

        // Configuración del evento onClick para abrir la página web del CURP.
        textView.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://consultas.curp.gob.mx/CurpSP/"))
            startActivity(intent)
        }

        // Configuración del TextView para consultar los términos y condiciones.
        val tvTerminosCondiciones: TextView = view.findViewById(R.id.tvTerminosCondiciones)
        val spannableStringTerminos = SpannableString("Consultar Términos y Condiciones")
        spannableStringTerminos.setSpan(
            URLSpan(""),
            0,
            spannableStringTerminos.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvTerminosCondiciones.text = spannableStringTerminos

        // Configuración del evento onClick para abrir la página web de los términos y condiciones.
        tvTerminosCondiciones.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://54.164.8.30:8080/terminosCondiciones.html"))
            startActivity(intent)
        }
    }
}
