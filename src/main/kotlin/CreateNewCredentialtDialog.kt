import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreateNewCredentialsDialog(
    modifier: Modifier = Modifier,
    projects:List<String>,
    onDismiss:() -> Unit = {},
    onSave:(project:String, s1:String, s2:String) -> Unit = {_,_,_ ->}
) {
    var inputText by remember { mutableStateOf("") }
    var inputText2 by remember { mutableStateOf("") }

    var selectedProject by remember { mutableStateOf(projects.firstOrNull() ?: "") }
    var expanded by remember { mutableStateOf(false) }


    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                onSave(selectedProject,inputText, inputText2)
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

                //add dropdown spinner for projects here
                Text(text = "Select Project", style = MaterialTheme.typography.body1)
                Box(modifier = Modifier.fillMaxWidth().clickable {
                    expanded = true
                }) {

                    Card(
                        modifier = Modifier.fillMaxWidth().clickable {
                            expanded = true
                        },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(0.5.dp, color = Color.Gray)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (selectedProject.isBlank()){
                                Text("Project", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                            }else{
                                Text(selectedProject, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                            }

                            IconButton(onClick = { expanded = true }) {
                                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
                            }
                        }
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        projects.forEach { project ->
                            DropdownMenuItem(onClick = {
                                selectedProject = project
                                expanded = false
                            }) {
                                Text(text = project)
                            }
                        }
                    }
                }



                OutlinedTextField(
                    modifier = Modifier.padding(top = 10.dp).fillMaxWidth(),
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("email") },
                    singleLine = true
                )

                OutlinedTextField(
                    modifier = Modifier.padding(top = 10.dp).fillMaxWidth(),
                    value = inputText2,
                    onValueChange = { inputText2 = it },
                    label = { Text("password") },
                    singleLine = true
                )
            }
        }
    )

}