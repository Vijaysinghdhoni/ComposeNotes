package com.vsdhoni5034.notesapp.feature_note.presentation.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsdhoni5034.notesapp.feature_note.domain.model.Note
import com.vsdhoni5034.notesapp.feature_note.domain.use_case.NoteUseCases
import com.vsdhoni5034.notesapp.feature_note.domain.util.NoteOrder
import com.vsdhoni5034.notesapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    var noteState by mutableStateOf(NotesState())
        private set

    private var lastRestoredNote: Note? = null
    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Desecending))
    }

    fun onNoteEvent(event: NotesEvent) {

        when (event) {

            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(event.note)
                    lastRestoredNote = event.note
                }
            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(lastRestoredNote ?: return@launch)
                    lastRestoredNote = null
                }
            }

            is NotesEvent.Order -> {

                if (noteState.noteOrder.orderType == event.noteOrder.orderType &&
                    noteState.noteOrder::class == event.noteOrder::class
                ) {
                    return
                }
                getNotes(event.noteOrder)
            }

            is NotesEvent.ToggleOrderSection -> {
                noteState = noteState.copy(
                    isOrderSectionVisible = !noteState.isOrderSectionVisible
                )
            }

        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotesUseCase(noteOrder).onEach { notes ->
            noteState = noteState.copy(
                notes = notes,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)

    }


}