package mx.itesm.appreto2.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.itesm.appreto2.ViewModel.Usuario3


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

class SharedViewModel : ViewModel() {
    // LiveData para el usuario.
    // Este LiveData se puede observar desde cualquier fragmento o actividad que tenga acceso a esta ViewModel.
    // Cuando los datos del usuario cambian, todos los observadores serán notificados.
    val usuarioLiveData: MutableLiveData<Usuario3?> = MutableLiveData()
}
