package mx.itesm.appreto2.View

import mx.itesm.appreto2.ViewModel.EncargadoInicioViewModel
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mx.itesm.appreto2.Model.MyApp
import mx.itesm.appreto2.R
import mx.itesm.appreto2.Model.User


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

// Clase encargadoInicio que hereda de Fragment.
// Esta clase se encarga de manejar la vista de inicio del encargado.
class encargadoInicio : Fragment() {

    // Instancia del ViewModel para el inicio del encargado y una variable para el usuario seleccionado.
    private lateinit var viewModel: EncargadoInicioViewModel
    private var selectedUserExtra: String? = null

    // Método que se llama cuando se crea la vista del fragmento.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla la vista con el layout correspondiente.
        val view = inflater.inflate(R.layout.fragment_encargado_inicio, container, false)

        // Referencias a la barra de búsqueda y a la lista de usuarios.
        val searchView = view.findViewById<SearchView>(R.id.searchView)
        val listView = view.findViewById<ListView>(R.id.userList)

        // Inicialización del ViewModel.
        viewModel = EncargadoInicioViewModel(requireActivity())

        // Obtiene los usuarios del ViewModel y los muestra en la lista.
        viewModel.getUsers { userList ->
            activity?.runOnUiThread {
                var adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, userList)
                listView.adapter = adapter

                // Configuración de la barra de búsqueda para filtrar los usuarios.
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText != null) {
                            val filteredList = userList.filter { it.toString().contains(newText, ignoreCase = true) }
                            adapter = ArrayAdapter(activity!!, android.R.layout.simple_list_item_1, filteredList)
                            listView.adapter = adapter
                        }
                        return false
                    }
                })

                // Configuración del evento al seleccionar un usuario de la lista.
                listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                    val selectedUser = listView.getItemAtPosition(position) as User
                    val selectedUserId = selectedUser.idUsuario

                    if (selectedUserExtra == null) {
                        val sharedPreferences = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences?.edit()
                        editor?.apply {
                            putString("SELECTED_USER", selectedUser.toString())
                            putInt("SELECTED_USER_ID", selectedUserId.toString().toInt())
                            commit()

                            println(selectedUser)
                            println(selectedUserId)

                            MyApp.idUsuario = selectedUserId
                        }

                        if (findNavController().currentDestination?.id == R.id.encargadoHome) {
                            findNavController().navigate(R.id.action_encargadoHome_to_pedidos)
                        }
                    } else {
                        selectedUserExtra = selectedUser.toString()
                        val filteredList = userList.filter { it.toString() == selectedUserExtra }
                        adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, filteredList)
                        listView.adapter = adapter
                    }
                }
            }
        }

        return view
    }
}
