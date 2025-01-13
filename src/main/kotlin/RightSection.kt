import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RightSection(
    modifier: Modifier = Modifier,
    showAllPassword: Boolean = false,
    projectCreds: List<Pair<String, String>>,
    onSendCredentials:(Pair<String, String>) -> Unit = {}
) {

    if (projectCreds.isEmpty()){
        Column(
            modifier = modifier.padding(top = 10.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Empty !!", modifier = Modifier, fontWeight = FontWeight.Medium, fontSize = 18.sp)
        }
    }else{
        LazyColumn(
            modifier = modifier.padding(top = 10.dp, bottom = 10.dp)
        ) {
            projectCreds.forEach { cred ->
                item {
                    var showThisCreds by remember { mutableStateOf(false) }
                    Card(
                        modifier = Modifier.wrapContentWidth().padding(vertical = 5.dp, horizontal = 10.dp),
                        border = BorderStroke(width = 0.5.dp, color = Color.Gray.copy(alpha = 0.5f))
                    ) {
                        Row(modifier = Modifier.padding(10.dp).wrapContentWidth().wrapContentHeight(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween) {

                            Column(
                                modifier = Modifier.weight(1f).wrapContentHeight(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(cred.first, modifier = Modifier, fontWeight = FontWeight.Medium, fontSize = 18.sp)
                                Spacer(modifier = Modifier.height(5.dp))
                                Row(
                                    modifier = Modifier.wrapContentHeight(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(if (showAllPassword || showThisCreds){
                                        cred.second
                                    }else {
                                        "*".repeat(cred.second.length)
                                    }, modifier = Modifier, textAlign = TextAlign.Center, fontWeight = FontWeight.Normal, fontSize = 18.sp)

                                    if (showAllPassword.not()){
                                        Spacer(modifier = Modifier.width(10.dp))

                                        Text(if (showThisCreds){
                                            "hide"
                                        }else {
                                            "show"
                                        }, modifier = Modifier.clickable {
                                            showThisCreds = showThisCreds.not()
                                        }, textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 12.sp,
                                            color = Color.Blue
                                        )
                                    }
                                }
                            }

                            Icon(
                                modifier = Modifier.size(30.dp).clickable {
                                    onSendCredentials(cred)
                                },
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "ENTER THIS CREDENTIALS",
                                tint = Color.Gray
                            )

                        }
                    }
                }
            }
        }
    }

}