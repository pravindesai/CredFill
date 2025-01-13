import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

object AppUtil {

    @Composable
    fun Int.pxToDp() = with(LocalDensity.current) {
        this@pxToDp.toDp()
    }


}