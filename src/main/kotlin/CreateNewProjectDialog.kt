import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateNewProjectDialog(
    modifier: Modifier = Modifier,
    onDismiss:() -> Unit = {},
    onSave:(string:String) -> Unit = {}
) {
    var inputText by remember { mutableStateOf("") }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                onSave(inputText)
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text("Cancel")
            }
        },
        title = {
            Column {
                Text(text = "Enter Your Input")
            }
        },
        text = {
            Column(modifier = Modifier) {
                OutlinedTextField(
                    modifier = Modifier.padding(top = 10.dp).fillMaxWidth(),
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Input") },
                    singleLine = true
                )
            }
        }
    )

}