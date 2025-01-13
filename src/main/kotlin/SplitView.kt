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

@Preview
@Composable
fun SplitView(modifier: Modifier = Modifier, windowSize: IntSize? = null,
              onSendCredentials:(Pair<String, String>) -> Unit = {},
              onChangeFocusable:(isFocusable:Boolean) -> Unit = {},
              appViewModel: AppViewModel = AppViewModel()) {

    var projectAndCredMap by remember {
        mutableStateOf(
            mapOf<String, List<Pair<String, String>>>()
        )
    }
    var selectedProject: String? by remember { mutableStateOf(projectAndCredMap.keys.firstOrNull()) }

    var showAllPassword: Boolean by remember { mutableStateOf(false) }

    fun checkData() {
        for (projects in projectAndCredMap) {
            println("-------------------------------")
            println(projects.key)
            projects.value.forEach {
                println("----${it.first}  -  ${it.second}")
            }
        }
    }

    LaunchedEffect(Unit) {
        //fill dummy data
        val map = mutableMapOf<String, List<Pair<String, String>>>()
        map.put(
            "project1", listOf(
                Pair("user1@example.com", "Password123"),
                Pair("user2@example.com", "Password456"),
                Pair("user3@example.com", "Password789"),
                Pair("user4@example.com", "Password321"),
                Pair("user5@example.com", "Password654"),
                Pair("user6@example.com", "Password987"),
                Pair("user7@example.com", "Password111"),
                Pair("user9@example.com", "Password123"),
                Pair("user9@example.com", "Password456"),
                Pair("user10@example.com", "Password789"),
                Pair("user11@example.com", "Password321"),
                Pair("user12@example.com", "Password654"),
                Pair("user13@example.com", "Password987"),
                Pair("user14@example.com", "Password111")
            )
        )

// Adding data for project2
        map.put(
            "project2", listOf(
                Pair("user8@example.com", "Password222"),
                Pair("user9@example.com", "Password333"),
                Pair("user10@example.com", "Password444"),
                Pair("user11@example.com", "Password555"),
                Pair("user12@example.com", "Password666"),
                Pair("user13@example.com", "Password777"),
                Pair("user14@example.com", "Password888")
            )
        )

// Adding data for project3
        map.put(
            "project3", listOf(
                Pair("user15@example.com", "Password999"),
                Pair("user16@example.com", "Password000"),
                Pair("user17@example.com", "Password101"),
                Pair("user18@example.com", "Password202"),
                Pair("user19@example.com", "Password303"),
                Pair("user20@example.com", "Password404")
            )
        )

        projectAndCredMap = map
        selectedProject = projectAndCredMap.keys.firstOrNull()
    }

    Row(modifier = modifier.fillMaxWidth().fillMaxHeight()) {

        LeftSection(
            modifier = Modifier.fillMaxHeight().weight(0.35f).background(color = Color.Gray).padding(end = 0.5.dp).background(color = Color.White).padding(10.dp),
            projectNames = projectAndCredMap.keys,
            selectedProjectName = selectedProject,
            onChangeFocusable = onChangeFocusable,
            showAllPassword = showAllPassword,
            addNewProject = { projectName ->
                val map = projectAndCredMap.toMutableMap()
                map[projectName] = map[projectName] ?: emptyList()
                projectAndCredMap = map
                checkData()
            }, addNewCredentials = { credPair ->
                val allCredsForProject = (projectAndCredMap.get(credPair.first) ?: emptyList()).toMutableList()
                allCredsForProject.add(credPair.second)
                val map = projectAndCredMap.toMutableMap()
                map[credPair.first] = allCredsForProject
                projectAndCredMap = map
                checkData()
            }, selectProject = { selectThisProject ->
                selectedProject = selectThisProject
            }, onShowAllPasswordChanged = { show ->
                showAllPassword  = show
            }, onImportDb = {

            }, onExportDb = {

            }
        )

        RightSection(
            modifier = Modifier.fillMaxHeight().weight(0.65f).background(color = Color.White),
            projectCreds = projectAndCredMap[selectedProject] ?: emptyList(),
            onSendCredentials = onSendCredentials,
            showAllPassword = showAllPassword
        )
    }

}
