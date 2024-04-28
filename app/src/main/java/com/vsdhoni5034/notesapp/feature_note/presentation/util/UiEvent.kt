package com.vsdhoni5034.notesapp.feature_note.presentation.util

sealed class UiEvent {

    data class ShowMessage(val message: String) : UiEvent()

    data object SaveNote : UiEvent()

}