package com.vsdhoni5034.notesapp.feature_note.presentation.util

sealed class Screen(val route: String) {

    data object AddEditNoteScreen : Screen("add_edit_note")

    data object NoteScreen : Screen("note_screen")

}