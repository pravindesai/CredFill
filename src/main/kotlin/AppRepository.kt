import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class AppRepository {
    private val TEST_MODE = false
    private val fileName = "CredFillData.json"

    fun writeToFile(value: Map<String, List<Pair<String, String>>>) {
        val file = File(fileName)

        try {
            val jsonContent = Json.encodeToString(value)
            file.writeText(jsonContent)
            println("File created successfully: ${file.absolutePath}")
            println("File written: ${jsonContent}")
        } catch (e: Exception) {
            println("Error creating file: ${e.message}")
        }
    }

    fun readFile():Map<String, List<Pair<String, String>>>{
        return if (TEST_MODE){
           getTestData()
        }else{
            val file = File(fileName)

            return try {
                val jsonContent = file.readText()
                val data = Json.decodeFromString<Map<String, List<Pair<String, String>>>>(jsonContent)

                println("File read successfully: ${data}")

                data
            } catch (e: Exception) {
                println("Error reading or deserializing file: ${e.message}")
                emptyMap()
            }
        }
    }

    fun downloadDB():Boolean{
        val sourceFile = File(fileName) // Replace with the actual file path
        val downloadsPath = System.getProperty("user.home") + "/Downloads"
        val destinationFile = File(downloadsPath, sourceFile.name)

        try {
            Files.copy(
                sourceFile.toPath(),
                destinationFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            )
            println("DB Exported successfully.")

            return true
        } catch (e: Exception) {
            println("Error copying file: ${e.message}")
            return false
        }

    }

    fun uploadDB(fileToImport:String):Boolean{
        val sourceFile = File(fileToImport) // Replace with the actual file path
        val destinationFile = File(fileName)

        try {
            Files.copy(
                sourceFile.toPath(),
                destinationFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            )
            println("DB Imported successfully.")

            return true
        } catch (e: Exception) {
            println("Error copying file: ${e.message}")
            println("Error copying file: ${e.cause}")
            return false
        }

    }
}

private fun getTestData():Map<String, List<Pair<String, String>>> {
    val testMap = mutableMapOf<String, List<Pair<String, String>>>()
    testMap.put(
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

    testMap.put(
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

    testMap.put(
        "project3", listOf(
            Pair("user15@example.com", "Password999"),
            Pair("user16@example.com", "Password000"),
            Pair("user17@example.com", "Password101"),
            Pair("user18@example.com", "Password202"),
            Pair("user19@example.com", "Password303"),
            Pair("user20@example.com", "Password404")
        )
    )
    return testMap
}
