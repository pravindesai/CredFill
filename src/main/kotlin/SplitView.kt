import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

object GlobalScopeViewModel{
    val appViewModel: AppViewModel = AppViewModel()
}

@Preview
@Composable
fun SplitView(modifier: Modifier = Modifier, windowSize: IntSize? = null,
              appViewModel: AppViewModel = viewModel { AppViewModel() },
              onSendCredentials:(Pair<String, String>) -> Unit = {},
              onChangeFocusable:(isFocusable:Boolean) -> Unit = {}) {

//    val appViewModel = GlobalScopeViewModel.appViewModel
    val projectAndCredMap = appViewModel.projectAndCredMap.collectAsState()
    val selectedProject = appViewModel.selectedProject.collectAsState()
    val showAllPassword = appViewModel.showAllPassword.collectAsState()

    val duplicateEmailError = appViewModel.duplicateEmailError.collectAsState()
    val duplicateProjectError = appViewModel.duplicateProjectError.collectAsState()

    if (duplicateProjectError.value){
        SimpleAlertDialog(
            title = "Project with this name already exits !!",
            hidePositiveButton = true,
            negativeButtonText = "OK",
            onDismiss = {
               appViewModel.clearError()
            }, onConfirm = {
                appViewModel.clearError()
            }
        )
    }

    if (duplicateEmailError.value){
        SimpleAlertDialog(
            title = "Credentials with this email already exists !!",
            hidePositiveButton = true,
            negativeButtonText = "OK",
            onDismiss = {
                appViewModel.clearError()
            }, onConfirm = {
                appViewModel.clearError()
            }
        )
    }

    fun checkData() {
        for (projects in projectAndCredMap.value) {
            println("-------------------------------")
            println(projects.key)
            projects.value.forEach {
                println("----${it.first}  -  ${it.second}")
            }
        }
    }

    Row(modifier = modifier.fillMaxWidth().fillMaxHeight()) {

        LeftSection(
            modifier = Modifier.fillMaxHeight().weight(0.35f).background(color = Color.Gray).padding(end = 0.5.dp).background(color = Color.White).padding(10.dp),
            projectNames = projectAndCredMap.value.keys,
            selectedProjectName = selectedProject.value,
            onChangeFocusable = onChangeFocusable,
            showAllPassword = showAllPassword.value,
            addNewProject = { projectName ->
                appViewModel.addProject(projectName)
                checkData()
            }, addNewCredentials = { credPair ->
                appViewModel.addCreds(credPair.first, credPair.second)
                checkData()
            }, selectProject = { selectThisProject ->
                appViewModel.updateSelectedProject(selectThisProject)
            }, onShowAllPasswordChanged = { show ->
                appViewModel.updateShowPasswordFlag(show)
            }, onImportDb = {
                appViewModel.importDb()
            }, onExportDb = {
                appViewModel.exportDb()
            }
        )

        RightSection(
            modifier = Modifier.fillMaxHeight().weight(0.65f).background(color = Color.White),
            selectedProjectName = selectedProject.value,
            projectCreds = projectAndCredMap.value[selectedProject.value] ?: emptyList(),
            onSendCredentials = onSendCredentials,
            showAllPassword = showAllPassword.value,
            onChangeFocusable = onChangeFocusable,
            onDeleteCredentials = { projectCred ->
                appViewModel.removeCreds(projectCred)
            }, onEditCredentials = { projectCreds, oldCreds ->

                appViewModel.editCreds(projectCreds, oldCreds)

            }
        )
    }

}
