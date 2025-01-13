import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun LeftSection(
    modifier: Modifier = Modifier,
    projectNames: Set<String>,
    selectedProjectName: String? = null,
    showAllPassword: Boolean = false,
    onChangeFocusable: (isFocusable: Boolean) -> Unit = {},
    addNewProject: (projectName: String) -> Unit = {},
    addNewCredentials: (Pair<String, Pair<String, String>>) -> Unit = {},
    selectProject: (project: String) -> Unit = {},
    onShowAllPasswordChanged: (Boolean) -> Unit = {},
    onImportDb: () -> Unit = {},
    onExportDb: () -> Unit = {}
) {

    var addNewProjectDialog by remember { mutableStateOf(false) }
    var addNewCredentialsDialog by remember { mutableStateOf(false) }


    if (addNewProjectDialog) {
        CreateNewProjectDialog(
            modifier = Modifier,
            onDismiss = {
                addNewProjectDialog = false
                onChangeFocusable(false)
            }, onSave = { projectName ->
                addNewProject(projectName)
                addNewProjectDialog = false
                onChangeFocusable(false)
            }
        )
    }

    if (addNewCredentialsDialog) {
        CreateNewCredentialsDialog(
            modifier = Modifier,
            projects = projectNames.toList(),
            onDismiss = {
                addNewCredentialsDialog = false
                onChangeFocusable(false)
            }, onSave = { project, s, s2 ->
                addNewCredentials(Pair(project, Pair(s, s2)))
                addNewCredentialsDialog = false
                onChangeFocusable(false)
            }
        )
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {
        Card(
            modifier = Modifier.wrapContentHeight().fillMaxWidth().clickable {
                addNewProjectDialog = true
                onChangeFocusable(true)
            },
            border = BorderStroke(0.5.dp, color = Color.Gray)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add New Project",
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Add New Project"
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.wrapContentHeight().fillMaxWidth().clickable {
                addNewCredentialsDialog = true
                onChangeFocusable(true)
            },
            border = BorderStroke(0.5.dp, color = Color.Gray)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Add New Credentials",
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Add New Credentials"
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier.fillMaxWidth().height(1.dp).padding(horizontal = 10.dp).background(color = Color.Gray)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                modifier = Modifier.weight(1f).clickable {
                    onImportDb()
                },
                border = BorderStroke(0.5.dp, color = Color.Gray)
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text("Import Data", modifier = Modifier.wrapContentSize())
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Import",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(5.dp))

            Card(
                modifier = Modifier.weight(1f).clickable {
                    onExportDb()
                },
                border = BorderStroke(0.5.dp, color = Color.Gray)
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text("Export Data", modifier = Modifier.wrapContentSize())
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Export",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }


        }

        Spacer(modifier = Modifier.height(10.dp))
        Card(
            modifier = Modifier.clickable {
                onShowAllPasswordChanged(showAllPassword.not())
            },
            border = BorderStroke(0.5.dp, color = Color.Gray)
        ){
            Row(modifier = Modifier.padding(horizontal = 5.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Show All Passwords", modifier = Modifier.weight(1f))
                Switch(
                    checked = showAllPassword,
                    onCheckedChange = { show ->
                        onShowAllPasswordChanged(show)
                    }

                )
            }
        }


        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier.fillMaxWidth().height(1.dp).padding(horizontal = 10.dp).background(color = Color.Gray)
        )
        Spacer(modifier = Modifier.height(1.dp))
        Divider(
            modifier = Modifier.fillMaxWidth().height(1.dp).padding(horizontal = 10.dp).background(color = Color.Gray)
        )
        Spacer(modifier = Modifier.height(5.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            projectNames.forEach { project ->
                item {
                    Card(
                        modifier = Modifier.padding(vertical = 3.dp, horizontal = 3.dp).fillMaxWidth()
                            .wrapContentHeight().clickable {
//                            selectedProject = project
                            selectProject(project)
                        },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(0.5.dp, color = Color.Gray),

                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().wrapContentHeight().background(color = if(project.equals(selectedProjectName)) Color.Green.copy(alpha = 0.1f) else Color.White),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            RadioButton(
                                selected = project.equals(selectedProjectName),
                                onClick = {
//                                    selectedProject = project
                                    selectProject(project)
                                }
                            )
                            Text(project, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

    }
}