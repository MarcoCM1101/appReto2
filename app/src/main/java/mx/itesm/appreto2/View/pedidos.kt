package mx.itesm.appreto2.View

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.appreto2.Model.MyApp
import mx.itesm.appreto2.R
import mx.itesm.appreto2.ViewModel.EncargadoInicioViewModel
import mx.itesm.appreto2.ViewModel.PedidosViewModel


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */

class pedidos : Fragment() {
    // Se inicializan variables
    private lateinit var adapter: MyAdapter
    private lateinit var searchView: SearchView
    private lateinit var listView: ListView

    private var selectedUserId: String? = null

    // Añade una variable para guardar el ID del usuario seleccionado en SecondUserList
    private var selectedSecondUserId: String? = null

    // Inicializa el ViewModel aquí
    private val viewModel = PedidosViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pedidos, container, false)
        val rbBoton: RadioButton = view.findViewById(R.id.rbDonativo)

        val sharedPreferences = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val selectedUser = sharedPreferences?.getString("SELECTED_USER", "")
        selectedUserId = selectedUser

        // Userlist es la lista a modificar
        val userList = mutableListOf(selectedUser ?: "")
        adapter = MyAdapter(userList) { clickedUserId ->
            // Actualiza la lista de usuarios cuando se selecciona un usuario
            updateUserList(clickedUserId)
            selectedUserId = clickedUserId
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.OneUserList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        val cbComidaExtra = view.findViewById<CheckBox>(R.id.rbComidaExtra)
        searchView = view.findViewById<SearchView>(R.id.Buscador)
        listView = view.findViewById<ListView>(R.id.SecondUserList)

        // Inicialmente ocultar SearchView y ListView
        searchView.visibility = View.GONE
        listView.visibility = View.GONE

        cbComidaExtra.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Mostrar SearchView y ListView cuando cbComidaExtra esté seleccionado (marcado)
                searchView.visibility = View.VISIBLE
                listView.visibility = View.VISIBLE

                // Aquí es donde se maneja la lógica cuando cbComidaExtra está seleccionado.
                val viewModel = EncargadoInicioViewModel(requireActivity())
                viewModel.getUsers { userList ->
                    activity?.runOnUiThread {
                        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, userList)
                        listView.adapter = adapter

                        listView.setOnItemClickListener { _, _, position, _ ->
                            val selectedUser = userList[position]
                            userList.clear()
                            userList.add(selectedUser)
                            adapter.notifyDataSetChanged()

                            // Guarda el ID del usuario seleccionado en SecondUserList
                            selectedSecondUserId = selectedUser.idUsuario.toString()
                        }
                    }
                }
            } else {
                // Ocultar SearchView y ListView cuando cbComidaExtra no esté seleccionado (desmarcado)
                searchView.visibility = View.GONE
                listView.visibility = View.GONE
            }
        }

        val btnusuarioComedor = view.findViewById<Button>(R.id.btnusuarioComedor)

        btnusuarioComedor.setOnClickListener {
            val donativo = if (rbBoton.isChecked) 1 else 0  // Si rbBoton está seleccionado, donativo será 1, de lo contrario será 0.

            // Pasar lista para el usuario seleccionado en OneUserList
            viewModel.enviarUsuario(MyApp.idUsuario, MyApp.idComedor, donativo)

            // Pasar lista para el usuario seleccionado en SecondUserList
            selectedSecondUserId?.let {
                viewModel.enviarUsuario(it.toInt(), MyApp.idComedor, 0)  // Donativo siempre será 0 para SecondUserList
            }

            // Muestra un Toast después de hacer la petición
            activity?.runOnUiThread {
                Toast.makeText(activity, "Pase de lista exitoso", Toast.LENGTH_SHORT).show()
            }
        }


        return view
    }

    private fun updateUserList(newUser: String) {
        adapter.filterUser(newUser)
    }

}

// Clase MyAdapter que hereda de RecyclerView.Adapter.
// Esta clase se encarga de manejar la lista de usuarios en un RecyclerView.
class MyAdapter(private var users: MutableList<String>, private val onItemClickListener: (String) -> Unit) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {


    // Esta clase se encarga de manejar la vista de cada elemento en el RecyclerView.
    inner class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView) {
        init {
            // Configura el evento onClick del TextView para notificar al oyente cuando se hace clic en un usuario.
            textView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedUser = users[position]
                    onItemClickListener(clickedUser)
                }
            }
        }
    }

    // Método para crear un nuevo ViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = TextView(parent.context)
        return ViewHolder(textView)
    }

    // Método para vincular los datos del usuario con el ViewHolder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = users[position]
    }

    // Método para obtener la cantidad de usuarios.
    override fun getItemCount() = users.size

    // Método para filtrar la lista de usuarios por un usuario específico.
    fun filterUser(user: String) {
        users.clear()
        users.add(user)
        notifyDataSetChanged()
    }
}
