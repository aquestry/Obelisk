package dev.aquestry.obelisk.storage

import dev.aquestry.obelisk.model.Group
import dev.aquestry.obelisk.model.Settings
import dev.aquestry.obelisk.model.User
import java.util.UUID

interface StorageManager {
    suspend fun loadUsers(): MutableSet<User>
    suspend fun loadUser(uuid: UUID): User?
    suspend fun loadGroups(): MutableSet<Group>
    suspend fun loadGroup(name: String): Group?
    suspend fun loadSettings()
    suspend fun saveUsers()
    suspend fun saveUser(user: User)
    suspend fun saveGroups()
    suspend fun saveGroup(group: Group)
    suspend fun saveSettings(settings: Settings)
}