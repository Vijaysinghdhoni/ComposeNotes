package com.vsdhoni5034.notesapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vsdhoni5034.notesapp.feature_note.domain.util.NoteOrder
import com.vsdhoni5034.notesapp.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Desecending),
    onOrderChange: (NoteOrder) -> Unit
) {

    Column(
        modifier = modifier
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            DefaultRadioButton(
                selected = noteOrder is NoteOrder.Title,
                onSelect = {
                    onOrderChange(NoteOrder.Title(noteOrder.orderType))
                },
                text = "Title"
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                selected = noteOrder is NoteOrder.Date,
                onSelect = {
                    onOrderChange(NoteOrder.Date(noteOrder.orderType))
                },
                text = "Date"
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                selected = noteOrder is NoteOrder.Color,
                onSelect = {
                    onOrderChange(NoteOrder.Color(noteOrder.orderType))
                },
                text = "Color"
            )

        }
        

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            DefaultRadioButton(
                selected = noteOrder.orderType is OrderType.Asecending,
                onSelect = {
                    onOrderChange(
                        noteOrder.copy(
                            OrderType.Asecending
                        )
                    )
                },
                text = "Ascending"
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                selected = noteOrder.orderType is OrderType.Desecending,
                onSelect = {
                    onOrderChange(
                        noteOrder.copy(
                            OrderType.Desecending
                        )
                    )
                },
                text = "Descending"
            )
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Pre() {
    OrderSection {
    }
}