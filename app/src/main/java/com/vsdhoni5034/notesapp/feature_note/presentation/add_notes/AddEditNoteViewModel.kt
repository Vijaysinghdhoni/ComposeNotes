package com.vsdhoni5034.notesapp.feature_note.presentation.add_notes

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsdhoni5034.notesapp.feature_note.domain.model.InValidNoteException
import com.vsdhoni5034.notesapp.feature_note.domain.model.Note
import com.vsdhoni5034.notesapp.feature_note.domain.use_case.NoteUseCases
import com.vsdhoni5034.notesapp.feature_note.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //how a new note is saving with null id ? when we are giving a proper id to it
    var noteTitleState by mutableStateOf(
        NoteTextFieldState(
            hint = "Enter title..."
        )
    )
        private set

    var noteContentState by mutableStateOf(
        NoteTextFieldState(
            hint = "Enter some content"
        )
    )
        private set

    var noteColorState by mutableIntStateOf(Note.noteColors.random().toArgb())
        private set

    private val eventFlow = MutableSharedFlow<UiEvent>()
    val _eventFlow = eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteUseCase(noteId)?.also { note ->
                        currentNoteId = note.id
                        noteTitleState = noteTitleState.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        noteContentState = noteContentState.copy(
                            text = note.content,
                            isHintVisible = false
                        )

                        noteColorState = note.color
                    }
                }
            }
        }
    }

    fun onEvent(addEditNoteEvent: AddEditNoteEvent) {
        when (addEditNoteEvent) {
            is AddEditNoteEvent.EnteredTitle -> {
                noteTitleState = noteTitleState.copy(
                    text = addEditNoteEvent.title
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                noteTitleState = noteTitleState.copy(
                    isHintVisible = !addEditNoteEvent.focusState.isFocused &&
                            noteTitleState.text.isEmpty()
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                noteContentState = noteContentState.copy(
                    text = addEditNoteEvent.content
                )
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                noteContentState = noteContentState.copy(
                    isHintVisible = !addEditNoteEvent.focusState.isFocused &&
                            noteContentState.text.isEmpty()
                )
            }

            is AddEditNoteEvent.ChangeColor -> {
                noteColorState = addEditNoteEvent.color
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        val note = Note(
                            title = noteTitleState.text,
                            content = noteContentState.text,
                            color = noteColorState,
                            timeStamp = System.currentTimeMillis(),
                            id = currentNoteId
                        )
                        noteUseCases.addNoteUseCase(
                            note
                        )
                        Log.d("Mytag", "note is $note")
                        eventFlow.emit(UiEvent.SaveNote)
                    } catch (ex: InValidNoteException) {
                        Log.d("Mytag", "error is ${ex.message}")
                        eventFlow.emit(
                            UiEvent.ShowMessage(
                                ex.message ?: "Error in Saving Note try later!"
                            )
                        )
                    }
                }
            }
        }
    }

}