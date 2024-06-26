package com.vsdhoni5034.notesapp.feature_note.presentation.notes

import com.vsdhoni5034.notesapp.feature_note.domain.model.Note
import com.vsdhoni5034.notesapp.feature_note.domain.util.NoteOrder
import com.vsdhoni5034.notesapp.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Desecending),
    val isOrderSectionVisible : Boolean = false
)