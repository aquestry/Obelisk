package dev.aquestry.obelisk.core

import dev.aquestry.obelisk.Obelisk
import dev.aquestry.obelisk.Obelisk.logger
import dev.aquestry.obelisk.Obelisk.settings
import dev.aquestry.obelisk.model.Group
import dev.aquestry.obelisk.model.Settings
import dev.aquestry.obelisk.model.User
import kotlinx.coroutines.runBlocking

object Cache {
    var groups = mutableSetOf<Group>()
    var users = mutableSetOf<User>()

    fun load() = runBlocking {
        Obelisk.storageManager.loadSettings()

        if(settings == null) {
            settings = Settings()
            logger.info("Using default settings!")
        }

        groups = Obelisk.storageManager.loadGroups()
        users = Obelisk.storageManager.loadUsers()
    }
}