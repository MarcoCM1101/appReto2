package mx.itesm.appreto2.View

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import mx.itesm.appreto2.Model.MyApp
import mx.itesm.appreto2.R
import mx.itesm.appreto2.ViewModel.EncargadoSettingsViewModel



/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase encargadoSettings que hereda de Fragment.
// Esta clase se encarga de manejar la vista de configuración del encargado.
class encargadoSettings : Fragment(R.layout.fragment_encargado_settings) {

    // Instancia del ViewModel para la configuración del encargado.
    private val viewModel = EncargadoSettingsViewModel()

    // Método que se llama cuando la vista del fragmento ha sido creada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referencia al grupo de botones de radio para el estado.
        val radioGroup = view.findViewById<RadioGroup>(R.id.Estatusbtn)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            // Obtiene el botón de radio seleccionado y actualiza el estado en el ViewModel.
            val radioButton = view.findViewById<RadioButton>(checkedId)
            val newStatus = if (radioButton.text == "ABIERTO") "Activo" else "Inactivo"
            viewModel.updateStatus(newStatus)
        }

        // Configuración del botón para administrar el inventario.
        val btnAdministrarInv = view.findViewById<Button>(R.id.btnAdministrarInv)
        btnAdministrarInv.setOnClickListener {
            val idComedor = MyApp.idComedor
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://54.164.8.30:8080/inventario.html?idComedor=$idComedor"))
            startActivity(intent)
        }

        // Configuración del botón para confirmar la llegada de inventario.
        val btnLlegoInventario = view.findViewById<Button>(R.id.btnLlegoInventario)
        btnLlegoInventario.setOnClickListener {
            AlertDialog.Builder(context).apply {
                setTitle("Confirmación")
                setMessage("¿Estás seguro que llegó inventario?")
                setPositiveButton("Sí") { _, _ ->
                    viewModel.llegoInventario()
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            }.create().show()
        }
    }
}
