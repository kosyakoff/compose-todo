package test.app.todocompose.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import test.app.todocompose.R

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit
) {
    if (!openDialog) {
        return
    }

    AlertDialog(title = {
        Text(
            text = title,
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Bold
        )
    }, text = {
        Text(
            text = message,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Normal
        )
    },
        confirmButton = {
            Button(onClick = {
                onYesClicked()
                closeDialog()
            }) {
                Text(text = stringResource(R.string.general_yes))
            }
        },
        dismissButton = {
            OutlinedButton(onClick = {
                closeDialog()
            }) {
                Text(text = stringResource(R.string.general_no))
            }
        },
        onDismissRequest = { closeDialog() }
    )
}