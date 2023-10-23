package mx.itesm.appreto2.View

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.zxing.integration.android.IntentIntegrator
import mx.itesm.appreto2.Model.MyApp
import mx.itesm.appreto2.R
import mx.itesm.appreto2.ViewModel.SharedViewModel
import mx.itesm.appreto2.databinding.FragmentEncargadoCameraBinding
import org.json.JSONObject


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase encargadoCamera que hereda de Fragment.
// Esta clase se encarga de manejar la vista para agregar una persona usando la cámara.
class encargadoCamera : Fragment(R.layout.fragment_encargado_camera) {

    // Instancia del ViewModel compartido y del binding.
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentEncargadoCameraBinding

    // Método que se llama cuando se crea la vista del fragmento.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEncargadoCameraBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    // Método que se llama cuando la vista del fragmento ha sido creada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEncargadoCameraBinding.bind(view)

        // Observa los cambios en los datos del usuario.
        sharedViewModel.usuarioLiveData.observe(viewLifecycleOwner) { usuario ->
            usuario?.let {

            }
        }

        // Configuración del botón para escanear un código QR.
        binding.btnQRCode.setOnClickListener {
            val integrator = IntentIntegrator.forSupportFragment(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            integrator.setPrompt("Scan a QR code")
            integrator.initiateScan()
        }
    }

    // Método que se llama cuando se recibe un resultado de una actividad.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                // Aquí, simplemente estamos mostrando el contenido del QR.

                val prefs = activity?.getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)
                prefs!!.edit().apply{
                    val jsonObject = JSONObject(result.contents)
                    println(jsonObject)
                    putString("SELECTED_USER", jsonObject.getString("nombre") + " " + jsonObject.getString("apellido") + "\n" + jsonObject.getString("curp"))
                    putInt("SELECTED_USER_ID", jsonObject.getInt("idUsuario"))
                    commit()

                    MyApp.idUsuario = jsonObject.getInt("idUsuario")
                }

                // Navega a la vista de pedidos después de escanear el código QR.
                findNavController().navigate(R.id.action_encargadoHome_to_pedidos)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}