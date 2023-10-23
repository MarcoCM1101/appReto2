package mx.itesm.appreto2.Model


/**
 * @author: Oswaldo Daniel Hernandez De Luna A01753911
 * @author: Adolfo Sebastian González Mora A01754412
 * @author: Jorge Daniel Rea Prado A01747327
 * @author: Marco Antonio Caudillo Morales A01753729
 */


// User es una clase de datos que representa a un usuario.
data class User(
    // idUsuario: Variable para almacenar el ID del usuario.
    val idUsuario: Int,

    // nombre: Variable para almacenar el nombre del usuario.
    val nombre: String,

    // apellido: Variable para almacenar el apellido del usuario.
    val apellido: String,

    // curp: Variable para almacenar el CURP del usuario.
    val curp: String
) {
    // Sobrescribimos el método toString para mostrar la información del usuario de una manera específica.
    override fun toString(): String {
        return "$nombre $apellido\n$curp"
    }
}
