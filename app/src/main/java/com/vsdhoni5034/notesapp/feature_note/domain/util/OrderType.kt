package com.vsdhoni5034.notesapp.feature_note.domain.util

sealed class OrderType {

    data object Asecending : OrderType()

    data object Desecending : OrderType()

}