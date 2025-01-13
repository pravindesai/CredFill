import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


fun main() = application {
    var focusabled by remember { mutableStateOf(false) }
    Window(onCloseRequest = ::exitApplication,
        title = "Cred Fill - Test credential manager for developers", resizable = false, focusable = focusabled, alwaysOnTop = false) {
        App(
            onChangeFocusable = { isFocusable ->
                focusabled = isFocusable
            }
        )
    }
}
