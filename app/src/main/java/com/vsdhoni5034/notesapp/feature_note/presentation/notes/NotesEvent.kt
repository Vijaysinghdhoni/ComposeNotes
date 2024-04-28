package com.vsdhoni5034.notesapp.feature_note.presentation.notes

import com.vsdhoni5034.notesapp.feature_note.domain.model.Note
import com.vsdhoni5034.notesapp.feature_note.domain.util.NoteOrder

sealed class NotesEvent {

    data object RestoreNote : NotesEvent()
    data object ToggleOrderSection : NotesEvent()

    data class DeleteNote(val note: Note) : NotesEvent()

    data class Order(val noteOrder: NoteOrder) : NotesEvent()

}