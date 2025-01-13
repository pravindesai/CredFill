import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState


fun main() = application {
    var focusabled by remember { mutableStateOf(false) }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Cred Fill - Test credential manager for developers",
        state = rememberWindowState(width = 680.dp, height = 650.dp),
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
