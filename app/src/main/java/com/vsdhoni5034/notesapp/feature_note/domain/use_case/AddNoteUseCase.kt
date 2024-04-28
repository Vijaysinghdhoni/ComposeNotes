package com.vsdhoni5034.notesapp.feature_note.domain.use_case

import com.vsdhoni5034.notesapp.feature_note.domain.model.InValidNoteException
import com.vsdhoni5034.notesapp.feature_note.domain.model.Note
import com.vsdhoni5034.notesapp.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(
    private val noteRepository: NoteRepository
) {

    //we check for business logic like validations in use-cases in clean architecture

    @Throws(InValidNoteException::class)
    suspend operator fun invoke(note: Note) {

        if (note.title.isEmpty()) {
            throw InValidNoteException("Title can not be empty")
        }

        if (note.content.isEmpty()) {
            throw InValidNoteException("Note content cannot be empty")
        }
        noteRepository.insertNote(note)
    }


}