package com.example.touska.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.touska.R
import com.example.touska.ui.theme.customColorsPalette

@Composable
fun CustomAlertDialog(
    onDismiss: () -> Unit,
    title: String,
    text: String,
    onConfirmButton: ()->Unit,
    onDismissButton: (() -> Unit)? = null

) {
    AlertDialog(
        backgroundColor = MaterialTheme.customColorsPalette.cardBack,
        shape = RoundedCornerShape(size = 12.dp),
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text)
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    contentColor = Color.White
                ),
                onClick = {
                    onConfirmButton()
                },

                ) {
                Text(stringResource(R.string.Confirm))
            }
        },
        dismissButton = {
            OutlinedButton(
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.Transparent
                ),
                onClick = {
                    onDismissButton ?: run {
                        onDismiss()
                    }
                }) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

