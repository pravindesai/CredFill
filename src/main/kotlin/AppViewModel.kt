import androidx.compose.runtime.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel {



    var _projectAndCredMap = MutableStateFlow<Map<String, List<Pair<String, String>>>>(mapOf())

    var _selectedProject = MutableStateFlow<String?>(null)


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

}