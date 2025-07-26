package dev.aquestry.obelisk.storage

import dev.aquestry.obelisk.model.Group
import dev.aquestry.obelisk.model.Settings
import dev.aquestry.obelisk.model.User
import java.util.UUID

class LocalSM : StorageManager {
    override suspend fun loadGroups(): MutableSet<Group> {
        TODO("Not yet implemented")
    }

    override suspend fun loadGroup(name: String): Group? {
        TODO("Not yet implemented")
    }

    override suspend fun loadSettings() {
        TODO("Not yet implemented")
    }

    override suspend fun saveUsers() {
        TODO("Not yet implemented")
    }

    override suspend fun loadUsers(): MutableSet<User> {
        TODO("Not yet implemented")
    }

    override suspend fun loadUser(uuid: UUID): User {
        TODO("Not yet implemented")
    }

    override suspend fun saveUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun saveGroups() {
        TODO("Not yet implemented")
    }

    override suspend fun saveGroup(group: Group) {
        TODO("Not yet implemented")
    }

    override suspend fun saveSettings(settings: Settings) {
        TODO("Not yet implemented")
    }
}