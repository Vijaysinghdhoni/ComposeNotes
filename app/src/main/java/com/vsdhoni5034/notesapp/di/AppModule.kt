package com.vsdhoni5034.notesapp.di

import android.app.Application
import androidx.room.Room
import com.vsdhoni5034.notesapp.feature_note.data.data_source.NoteDB
import com.vsdhoni5034.notesapp.feature_note.data.data_source.NoteDao
import com.vsdhoni5034.notesapp.feature_note.data.repository.NoteRepositoryImpl
import com.vsdhoni5034.notesapp.feature_note.domain.repository.NoteRepository
import com.vsdhoni5034.notesapp.feature_note.domain.use_case.AddNoteUseCase
import com.vsdhoni5034.notesapp.feature_note.domain.use_case.DeleteNoteUseCase
import com.vsdhoni5034.notesapp.feature_note.domain.use_case.GetNoteUseCase
import com.vsdhoni5034.notesapp.feature_note.domain.use_case.GetNotesUseCase
import com.vsdhoni5034.notesapp.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesNoteDatabase(app: Application): NoteDB {
        return Room.databaseBuilder(
            app,
            NoteDB::class.java,
            NoteDB.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesNoteDao(db: NoteDB): NoteDao {
        return db.noteDao
    }

    @Provides
    @Singleton
    fun providesNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(noteDao)
    }

    @Provides
    @Singleton
    fun providesNoteUseCases(noteRepository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(noteRepository),
            deleteNoteUseCase = DeleteNoteUseCase(noteRepository),
            addNoteUseCase = AddNoteUseCase(noteRepository),
            getNoteUseCase = GetNoteUseCase((noteRepository))
        )
    }

}