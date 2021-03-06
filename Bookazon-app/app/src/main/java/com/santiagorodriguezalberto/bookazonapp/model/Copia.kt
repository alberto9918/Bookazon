package com.santiagorodriguezalberto.bookazonapp.model

import java.util.*

data class Copia(

    val titulo: String,
    val autor: String,
    val resumen: String,
    val genero: String,
    val isbn: String,
    val editorial: String,
    val imagen: String,
    val numero_copia: Int,
    var esta_reservada: Boolean,
    val biblioteca: Biblioteca,
    val id: UUID

)