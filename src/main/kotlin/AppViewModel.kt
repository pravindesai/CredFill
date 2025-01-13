import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect

class AppViewModel : ViewModel(){

    private var _projectAndCredMap = MutableStateFlow<Map<String, List<Pair<String, String>>>>(mapOf())
    val projectAndCredMap = _projectAndCredMap.asStateFlow()

    private var _selectedProject = MutableStateFlow<String?>(null)
    var selectedProject = _selectedProject.asStateFlow()

    private var _showAllPassword = MutableStateFlow<Boolean>(false)
    val showAllPassword = _showAllPassword.asStateFlow()

    private var _duplicateProjectError = MutableStateFlow<Boolean>(false)
    val duplicateProjectError = _duplicateProjectError.asStateFlow()

    private var _duplicateEmailError = MutableStateFlow<Boolean>(false)
    val duplicateEmailError = _duplicateEmailError.asStateFlow()


    init {
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

        _projectAndCredMap.value = map
        _selectedProject.value = _projectAndCredMap.value.keys.firstOrNull()


    }

    fun updateShowPasswordFlag(showPass:Boolean){
        _showAllPassword.value  = showPass
    }

    fun updateSelectedProject(selectedProject:String){
        _selectedProject.value = selectedProject
    }

    fun addProject(project:String){
        if (_projectAndCredMap.value.keys.contains(project)){
            _duplicateProjectError.value  = true
            return
        }
        val map = _projectAndCredMap.value.toMutableMap()
        map[project] = map[project] ?: emptyList()
        _projectAndCredMap.value = map

    }

    fun addCreds(project:String, cred:Pair<String, String>){

        if (_projectAndCredMap.value[project]?.any { it.first.equals(cred.first) } == true){
            _duplicateEmailError.value  = true
            return
        }


        val allCredsForProject = (_projectAndCredMap.value.get(project) ?: emptyList()).toMutableList()
        allCredsForProject.add(cred)
        val map = projectAndCredMap.value.toMutableMap()
        map[project] = allCredsForProject
        _projectAndCredMap.value = map
    }

    fun importDb(){

    }

    fun exportDb(){

    }

    fun removeCreds(projectCred: Pair<String?, Pair<String, String>?>) {
        var allCredsForProject = (_projectAndCredMap.value.get(projectCred.first?:"") ?: emptyList()).toMutableList()
        allCredsForProject = allCredsForProject.filterNot { it.first.equals(projectCred.second?.first, ignoreCase = false) }.toMutableList()
        val map = projectAndCredMap.value.toMutableMap()
        map[projectCred.first?:""] = allCredsForProject
        _projectAndCredMap.value = map

    }

    fun editCreds(projectCred: Pair<String?, Pair<String, String>?>, oldCreds:Pair<String, String>?) {
        var allCredsForProject = (_projectAndCredMap.value.get(projectCred.first?:"") ?: emptyList()).toMutableList()
        allCredsForProject = allCredsForProject.filterNot { it.first.equals(oldCreds?.first, ignoreCase = false) }.toMutableList()

        projectCred.second?.let { allCredsForProject.add(it) }

        val map = projectAndCredMap.value.toMutableMap()

        map[projectCred.first?:""] = allCredsForProject
        _projectAndCredMap.value = map

    }

    fun clearError(){
        _duplicateEmailError.value = false
        _duplicateProjectError.value = false
    }

}