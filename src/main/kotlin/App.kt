import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import java.awt.Robot
import java.awt.event.KeyEvent

@Preview
@Composable
fun App(modifier: Modifier = Modifier, onChangeFocusable:(isFocusable:Boolean) -> Unit = {}) {
    var windowSize by remember { mutableStateOf(IntSize.Zero) }

    MaterialTheme {
        Column(modifier = modifier.fillMaxSize().onGloballyPositioned {
            windowSize = it.size
            println("Width: ${windowSize.width}")
            println("Height: ${windowSize.height}")
            println("----------------------------------------")
        }) {
            SplitView(windowSize = windowSize, onChangeFocusable = onChangeFocusable, onSendCredentials = { (email, pass) ->
                val emailArray = email.toCharArray().toMutableList()
                val passArray = pass.toCharArray().toMutableList()

                println("email - $emailArray")
                println("pass - $passArray")

                emailArray.forEach { char ->
                    try {
                        simulateKeyPress(char)
                    } catch (e: Exception) {
                        println("E:KEY CODE NOT FOUND for -> $char")
                        println("Exception ${e.message} ")
                    }
                }

                //TAB
                simulateTabPress()


                passArray.forEach { char ->
                    try {
                        simulateKeyPress(char)
                    } catch (e: Exception) {
                        println("E:KEY CODE NOT FOUND for -> $char")
                        println("Exception ${e.message} ")
                    }
                }

                //TAB
                simulateTabPress()
                //ENTER
                simulateEnterPress()

            })
        }
    }
}

data class KeyCodeWithModifier(val keyCode: Int, val requiresShift: Boolean)

fun charToKeyCode(char: Char): KeyCodeWithModifier? {
    return when (char) {
        // Letters a-z and A-Z
        'a', 'A' -> KeyCodeWithModifier(KeyEvent.VK_A, char.isUpperCase())
        'b', 'B' -> KeyCodeWithModifier(KeyEvent.VK_B, char.isUpperCase())
        'c', 'C' -> KeyCodeWithModifier(KeyEvent.VK_C, char.isUpperCase())
        'd', 'D' -> KeyCodeWithModifier(KeyEvent.VK_D, char.isUpperCase())
        'e', 'E' -> KeyCodeWithModifier(KeyEvent.VK_E, char.isUpperCase())
        'f', 'F' -> KeyCodeWithModifier(KeyEvent.VK_F, char.isUpperCase())
        'g', 'G' -> KeyCodeWithModifier(KeyEvent.VK_G, char.isUpperCase())
        'h', 'H' -> KeyCodeWithModifier(KeyEvent.VK_H, char.isUpperCase())
        'i', 'I' -> KeyCodeWithModifier(KeyEvent.VK_I, char.isUpperCase())
        'j', 'J' -> KeyCodeWithModifier(KeyEvent.VK_J, char.isUpperCase())
        'k', 'K' -> KeyCodeWithModifier(KeyEvent.VK_K, char.isUpperCase())
        'l', 'L' -> KeyCodeWithModifier(KeyEvent.VK_L, char.isUpperCase())
        'm', 'M' -> KeyCodeWithModifier(KeyEvent.VK_M, char.isUpperCase())
        'n', 'N' -> KeyCodeWithModifier(KeyEvent.VK_N, char.isUpperCase())
        'o', 'O' -> KeyCodeWithModifier(KeyEvent.VK_O, char.isUpperCase())
        'p', 'P' -> KeyCodeWithModifier(KeyEvent.VK_P, char.isUpperCase())
        'q', 'Q' -> KeyCodeWithModifier(KeyEvent.VK_Q, char.isUpperCase())
        'r', 'R' -> KeyCodeWithModifier(KeyEvent.VK_R, char.isUpperCase())
        's', 'S' -> KeyCodeWithModifier(KeyEvent.VK_S, char.isUpperCase())
        't', 'T' -> KeyCodeWithModifier(KeyEvent.VK_T, char.isUpperCase())
        'u', 'U' -> KeyCodeWithModifier(KeyEvent.VK_U, char.isUpperCase())
        'v', 'V' -> KeyCodeWithModifier(KeyEvent.VK_V, char.isUpperCase())
        'w', 'W' -> KeyCodeWithModifier(KeyEvent.VK_W, char.isUpperCase())
        'x', 'X' -> KeyCodeWithModifier(KeyEvent.VK_X, char.isUpperCase())
        'y', 'Y' -> KeyCodeWithModifier(KeyEvent.VK_Y, char.isUpperCase())
        'z', 'Z' -> KeyCodeWithModifier(KeyEvent.VK_Z, char.isUpperCase())

        // Numbers 0-9
        '0' -> KeyCodeWithModifier(KeyEvent.VK_0, false)
        '1' -> KeyCodeWithModifier(KeyEvent.VK_1, false)
        '2' -> KeyCodeWithModifier(KeyEvent.VK_2, false)
        '3' -> KeyCodeWithModifier(KeyEvent.VK_3, false)
        '4' -> KeyCodeWithModifier(KeyEvent.VK_4, false)
        '5' -> KeyCodeWithModifier(KeyEvent.VK_5, false)
        '6' -> KeyCodeWithModifier(KeyEvent.VK_6, false)
        '7' -> KeyCodeWithModifier(KeyEvent.VK_7, false)
        '8' -> KeyCodeWithModifier(KeyEvent.VK_8, false)
        '9' -> KeyCodeWithModifier(KeyEvent.VK_9, false)

        // Special characters
        '@' -> KeyCodeWithModifier(KeyEvent.VK_2, true) // Shift + 2
        '.' -> KeyCodeWithModifier(KeyEvent.VK_PERIOD, false)
        '!' -> KeyCodeWithModifier(KeyEvent.VK_1, true) // Shift + 1
        '#' -> KeyCodeWithModifier(KeyEvent.VK_3, true) // Shift + 3
        '$' -> KeyCodeWithModifier(KeyEvent.VK_4, true) // Shift + 4
        '%' -> KeyCodeWithModifier(KeyEvent.VK_5, true) // Shift + 5
        '^' -> KeyCodeWithModifier(KeyEvent.VK_6, true) // Shift + 6
        '&' -> KeyCodeWithModifier(KeyEvent.VK_7, true) // Shift + 7
        '*' -> KeyCodeWithModifier(KeyEvent.VK_8, true) // Shift + 8
        '(' -> KeyCodeWithModifier(KeyEvent.VK_9, true) // Shift + 9
        ')' -> KeyCodeWithModifier(KeyEvent.VK_0, true) // Shift + 0
        '_' -> KeyCodeWithModifier(KeyEvent.VK_MINUS, true) // Shift + -
        '+' -> KeyCodeWithModifier(KeyEvent.VK_EQUALS, true) // Shift + =
        else -> null // Unsupported characters
    }
}

// Function to simulate key press using Robot
fun simulateKeyPress(char: Char) {
    val robot = Robot()
    val keyCodeWithModifier = charToKeyCode(char)

    if (keyCodeWithModifier != null) {
        if (keyCodeWithModifier.requiresShift) {
            robot.keyPress(KeyEvent.VK_SHIFT)
        }
        robot.keyPress(keyCodeWithModifier.keyCode)
        robot.keyRelease(keyCodeWithModifier.keyCode)
        if (keyCodeWithModifier.requiresShift) {
            robot.keyRelease(KeyEvent.VK_SHIFT)
        }
    } else {
        println("Unsupported character: $char")
    }
}


fun simulateTabPress() {
    val robot = Robot()
    robot.keyPress(KeyEvent.VK_TAB)
    robot.keyRelease(KeyEvent.VK_TAB)
}

fun simulateEnterPress() {
    val robot = Robot()
    robot.keyPress(KeyEvent.VK_ENTER)
    robot.keyRelease(KeyEvent.VK_ENTER)
}
