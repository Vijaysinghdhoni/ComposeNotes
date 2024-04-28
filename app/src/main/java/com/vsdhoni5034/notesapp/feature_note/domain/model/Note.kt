package com.vsdhoni5034.notesapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vsdhoni5034.notesapp.feature_note.theme.BabyBlue
import com.vsdhoni5034.notesapp.feature_note.theme.LightBlue
import com.vsdhoni5034.notesapp.feature_note.theme.LightGreen
import com.vsdhoni5034.notesapp.feature_note.theme.RedOrange
import com.vsdhoni5034.notesapp.feature_note.theme.RedPink
import com.vsdhoni5034.notesapp.feature_note.theme.Violet

@Entity(tableName = "note_table")
data class Note(
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, RedPink, LightGreen, LightBlue, BabyBlue, Violet)
    }
}

class InValidNoteException(message:String) : Exception(message)
