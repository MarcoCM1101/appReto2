package mx.itesm.appreto2.View

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import mx.itesm.appreto2.R
import mx.itesm.appreto2.ViewModel.QrViewModel


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase Qr que hereda de Fragment.
// Esta clase se encarga de manejar la vista para generar códigos QR.
class Qr : Fragment() {

    // Instancia del ViewModel para el código QR.
    private lateinit var viewModel: QrViewModel

    // Método que se llama cuando se crea la vista del fragmento.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla la vista con el layout correspondiente.
        return inflater.inflate(R.layout.fragment_qr, container, false)
    }

    // Método que se llama cuando la vista del fragmento ha sido creada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialización del ViewModel.
        viewModel = ViewModelProvider(this).get(QrViewModel::class.java)

        // Referencias al botón para generar el código QR, al campo de texto para la CURP y a la imagen para el código QR.
        val btnGeneraQR = view.findViewById<Button>(R.id.btnGeneraQR)
        val txtCurp = view.findViewById<EditText>(R.id.tvCURP)
        val imgQRUsuario = view.findViewById<ImageView>(R.id.imgQRUsuario)

        // Configuración del botón para generar el código QR.
        btnGeneraQR.setOnClickListener {
            // Obtiene la CURP ingresada por el usuario y solicita al ViewModel que obtenga el usuario correspondiente.
            val curp = txtCurp.text.toString()
            viewModel.obtenerUsuario(curp)
        }

        // Observa los cambios en los datos del usuario en el ViewModel.
        viewModel.usuarioLiveData.observe(viewLifecycleOwner, Observer { usuario3 ->
            if (usuario3 != null) {
                // Si el usuario existe, genera un código QR con los datos del usuario y lo muestra en la imagen.
                val json = Gson().toJson(usuario3)
                println("JSON: $json")
                val bitmap = generateQRCode(json)
                imgQRUsuario.setImageBitmap(bitmap)
            }
        })

        // Observa si la operación fue exitosa en el ViewModel.
        viewModel.isSuccessful.observe(viewLifecycleOwner, Observer { isSuccessful ->
            if (!isSuccessful) {
                // Si la operación no fue exitosa, muestra un mensaje al usuario.
                Toast.makeText(context, "CURP no registrado", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Método para generar un código QR a partir de un texto.
    private fun generateQRCode(text: String): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) -16777216 else -1)
            }
        }
        return bitmap
    }
}
