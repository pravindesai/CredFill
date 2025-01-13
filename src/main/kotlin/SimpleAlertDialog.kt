
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleAlertDialog(
    modifier: Modifier = Modifier,
    hidePositiveButton:Boolean = false,
    negativeButtonText:String = "Cancel",
    title:String,
    onDismiss:() -> Unit = {},
    onConfirm:() -> Unit = {}
) {

    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onDismiss() },
        confirmButton = if (hidePositiveButton){
            { }
        }else{
            {
                TextButton(onClick = {
                    onConfirm()
                }) {
                    Text("Confirm")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(negativeButtonText)
            }
        },
        title = {
            Column {
                Text(text = title)
            }
        }
    )

}