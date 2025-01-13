import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState


fun main() = application {
    var focusabled by remember { mutableStateOf(false) }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Cred Fill - Test credential manager for developers",
        state = rememberWindowState(width = 700.dp, height = 600.dp),
        resizable = false,
        focusable = focusabled,
        alwaysOnTop = true
    ) {
        App(
            onChangeFocusable = { isFocusable ->
                focusabled = isFocusable
            }
        )
    }
}
