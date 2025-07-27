package dev.aquestry.obelisk.storage

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.aquestry.obelisk.Obelisk
import dev.aquestry.obelisk.core.Cache
import dev.aquestry.obelisk.model.Group
import dev.aquestry.obelisk.model.User
import java.io.File
import java.util.UUID

class LocalSM : StorageManager {
    private val mapper = jacksonObjectMapper()
    private val baseDir = File("./obelisk").apply { mkdirs() }
    private val usersFile = File(baseDir, "users.json")
    private val groupsFile = File(baseDir, "groups.json")
    private val settingsFile = File(baseDir, "settings.json")

    private fun ensureFile(file: File, resourceName: String) {
        if (!file.exists()) {
            javaClass.classLoader.getResourceAsStream(resourceName)?.use { input ->
                file.outputStream().use { output -> input.copyTo(output) }
            }
        }
    }

    override suspend fun loadGroups(): MutableSet<Group> {
        ensureFile(groupsFile, "groups.json")
        return mapper.readValue(groupsFile)
    }

    override suspend fun loadGroup(name: String): Group? {
        return loadGroups().find { it.name == name }
    }

    override suspend fun loadSettings() {
        ensureFile(settingsFile, "settings.json")
        Obelisk.settings = mapper.readValue(settingsFile)
    }

    override suspend fun saveUsers() {
        mapper.writerWithDefaultPrettyPrinter().writeValue(usersFile, Cache.users)
    }

    override suspend fun loadUsers(): MutableSet<User> {
        ensureFile(usersFile, "users.json")
        return mapper.readValue(usersFile)
    }

    override suspend fun loadUser(uuid: UUID): User? {
        return loadUsers().find { it.uuid == uuid }
    }

    override suspend fun saveUser(user: User) {
        val users = loadUsers()
        users.removeIf { it.uuid == user.uuid }
        users.add(user)
        mapper.writerWithDefaultPrettyPrinter().writeValue(usersFile, users)
    }

    override suspend fun saveGroups() {
        mapper.writerWithDefaultPrettyPrinter().writeValue(groupsFile, Cache.groups)
    }

    override suspend fun saveGroup(group: Group) {
        val groups = loadGroups()
        groups.removeIf { it.name == group.name }
        groups.add(group)
        mapper.writerWithDefaultPrettyPrinter().writeValue(groupsFile, groups)
    }

    override suspend fun saveSettings() {
        mapper.writerWithDefaultPrettyPrinter().writeValue(settingsFile, Obelisk.settings)
    }
}