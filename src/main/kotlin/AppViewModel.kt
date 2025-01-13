import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel : ViewModel(){

    private val appRepository = AppRepository()

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

    private var _loading = MutableStateFlow<Boolean>(false)
    val loading = _loading.asStateFlow()

    private var _export = MutableStateFlow<Boolean>(false)
    val export = _export.asStateFlow()

    private var _import = MutableStateFlow<Boolean>(false)
    val import = _import.asStateFlow()

    private var _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()


    init {
        readFile()
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

        writeToFile()

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

        writeToFile()
    }

    fun removeCreds(projectCred: Pair<String?, Pair<String, String>?>) {
        var allCredsForProject = (_projectAndCredMap.value.get(projectCred.first?:"") ?: emptyList()).toMutableList()
        allCredsForProject = allCredsForProject.filterNot { it.first.equals(projectCred.second?.first, ignoreCase = false) }.toMutableList()
        val map = projectAndCredMap.value.toMutableMap()
        map[projectCred.first?:""] = allCredsForProject
        _projectAndCredMap.value = map
        writeToFile()
    }

    fun editCreds(projectCred: Pair<String?, Pair<String, String>?>, oldCreds:Pair<String, String>?) {
        var allCredsForProject = (_projectAndCredMap.value.get(projectCred.first?:"") ?: emptyList()).toMutableList()
        allCredsForProject = allCredsForProject.filterNot { it.first.equals(oldCreds?.first, ignoreCase = false) }.toMutableList()

        projectCred.second?.let { allCredsForProject.add(it) }

        val map = projectAndCredMap.value.toMutableMap()

        map[projectCred.first?:""] = allCredsForProject
        _projectAndCredMap.value = map

        writeToFile()
    }

    fun clearError(){
        _duplicateEmailError.value = false
        _duplicateProjectError.value = false
        _export.value = false
        _import.value = false
        _error.value = null
    }

    private fun writeToFile(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            appRepository.writeToFile(_projectAndCredMap.value)
            _loading.value = false
        }
    }

    private fun readFile(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _projectAndCredMap.value = appRepository.readFile()

            if (_selectedProject.value.isNullOrBlank()){
                _selectedProject.value = _projectAndCredMap.value.keys.firstOrNull()
            }
            _loading.value = false
        }
    }

    fun importDb(fileToImport:String){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _import.value = appRepository.uploadDB(fileToImport)
            if (_import.value){
                readFile()
            }else{
                _error.value = "Failed to import data base.\nPlease verify integrity of data."
            }
            _loading.value = false

        }
    }

    fun exportDb(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _export.value = appRepository.downloadDB()
            _loading.value = false
        }
    }

}