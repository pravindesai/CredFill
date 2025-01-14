import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RightSection(
    modifier: Modifier = Modifier,
    showAllPassword: Boolean = false,
    selectedProjectName: String? = null,
    projectCreds: List<Pair<String, String>>,
    onSendCredentials:(Pair<String, String>) -> Unit = {},
    onChangeFocusable: (isFocusable: Boolean) -> Unit = {},
    onDeleteCredentials:(Pair<String?, Pair<String, String>?>) -> Unit = {},
    onEditCredentials:(Pair<String?, Pair<String, String>?>, oldCred:Pair<String, String>?) -> Unit = {_,_ ->},
) {
    var selectedCredes:Pair<String, String>? by remember { mutableStateOf(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    val clipboard: ClipboardManager = LocalClipboardManager.current
    var isToastVisible by remember { mutableStateOf(false) }
    var toastString by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    if (showDeleteDialog){
        SimpleAlertDialog(
            title = "Are you sure you want to delete this credentials from ${selectedProjectName} ?",
            onDismiss = {
                showDeleteDialog = false
                onChangeFocusable(false)
            }, onConfirm = {
                showDeleteDialog  = false
                onChangeFocusable(false)
                onDeleteCredentials(Pair(selectedProjectName, selectedCredes))
            }
        )
    }

    if (showEditDialog){
        CreateNewCredentialsDialog(
            modifier = Modifier,
            editView = true,
            selectedCredes = selectedCredes,
            projects = listOf(selectedProjectName?:""),
            onDismiss = {
                showEditDialog = false
                onChangeFocusable(false)
            }, onSave = { project, s, s2 ->
                showEditDialog = false
                onChangeFocusable(false)
                onEditCredentials(Pair(project, Pair(s, s2)), selectedCredes)
            }
        )
    }

    Box(
        modifier = modifier.padding(top = 10.dp, bottom = 10.dp)
    ){

        if (projectCreds.isEmpty()){
            Column(
                modifier = modifier.padding(top = 10.dp, bottom = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Empty !!", modifier = Modifier, fontWeight = FontWeight.Medium, fontSize = 18.sp)
            }
        }else{
            Column() {
                LazyColumn() {
                    projectCreds.forEach { cred ->
                        item {
                            var showThisCreds by remember(showAllPassword) { mutableStateOf(showAllPassword) }
                            Card(
                                modifier = Modifier.wrapContentWidth().padding(vertical = 5.dp, horizontal = 10.dp),
                                border = BorderStroke(width = 0.5.dp, color = Color.Gray.copy(alpha = 0.5f))
                            ) {
                                Row(modifier = Modifier.padding(8.dp).wrapContentWidth().wrapContentHeight(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween) {

                                    Column(
                                        modifier = Modifier.weight(1f).wrapContentHeight(),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Row(
                                            modifier = Modifier,
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start
                                        ) {
                                            Text(cred.first, modifier = Modifier, fontWeight = FontWeight.Medium, fontSize = 16.sp)
                                            Spacer(modifier = Modifier.width(10.dp))

                                            Icon(
                                                modifier = Modifier.size(12.dp).clickable {
                                                    //COPY EMAIL
                                                    clipboard.setText(AnnotatedString(cred.first))
                                                    toastString = "Email Copied"
                                                    isToastVisible = true
                                                    coroutineScope.launch {
                                                        delay(1500)
                                                        isToastVisible = false
                                                    }
                                                },
                                                contentDescription = "Copy",
                                                imageVector = Icons.Default.ContentCopy
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Row(
                                            modifier = Modifier.wrapContentHeight(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(if (showAllPassword || showThisCreds){
                                                cred.second
                                            }else {
                                                "*".repeat(cred.second.length)
                                            }, modifier = Modifier, textAlign = TextAlign.Center, fontWeight = FontWeight.Normal, fontSize = 14.sp)

                                            Spacer(modifier = Modifier.width(10.dp))

                                            Icon(
                                                modifier = Modifier.size(12.dp).clickable {
                                                    //COPY Password
                                                    clipboard.setText(AnnotatedString(cred.second))
                                                    toastString = "Password copied"
                                                    isToastVisible = true
                                                    coroutineScope.launch {
                                                        delay(1500)
                                                        isToastVisible = false
                                                    }

                                                },
                                                contentDescription = "Copy",
                                                imageVector = Icons.Default.ContentCopy
                                            )

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

                                    Column(
                                        modifier = Modifier.wrapContentSize(),
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(15.dp).clickable {
                                                selectedCredes = cred
                                                showEditDialog = true
                                                onChangeFocusable(true)
                                            },
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit",
                                            tint = Color.Blue
                                        )

                                        Spacer(modifier = Modifier.height(7.dp))

                                        Icon(
                                            modifier = Modifier.size(15.dp).clickable {
                                                selectedCredes = cred
                                                showDeleteDialog = true
                                                onChangeFocusable(true)
                                            },
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = Color.Red
                                        )

                                    }

                                    Spacer(modifier = Modifier.width(15.dp))

                                    Icon(
                                        modifier = Modifier.size(20.dp).clickable {
                                            selectedCredes = cred
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

        Toast(message = toastString, isVisible = isToastVisible)

    }



}


@Composable
fun Toast(message: String, isVisible: Boolean) {
    LaunchedEffect(message, isVisible){
        println("---${message} = ${isVisible}")
    }
    if (isVisible) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Card(
                modifier = Modifier.padding(16.dp),
                elevation = 50.dp,
                border = BorderStroke(0.5.dp, color = Color.Gray.copy(alpha = 0.2f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = message,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(10.dp),
                )
            }
        }
    }
}
