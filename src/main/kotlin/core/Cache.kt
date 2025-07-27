package dev.aquestry.obelisk.core

import dev.aquestry.obelisk.Obelisk.storageManager
import dev.aquestry.obelisk.Obelisk.logger
import dev.aquestry.obelisk.model.Config
import dev.aquestry.obelisk.model.Group
import dev.aquestry.obelisk.model.User
import kotlinx.coroutines.runBlocking

object Cache {
    var groups = mutableSetOf<Group>()
    var users = mutableSetOf<User>()

    val defaultGroup = Group(
        "default",
        Config(
            emptySet(),
            "",
            "<prefix> <username> <suffixTags>",
            "<prefix> <username> <suffixTags>: <messageFormatted>",
            emptyList()
        ),
        0
    )

    fun load() = runBlocking {
        storageManager.loadSettings()

        groups = storageManager.loadGroups()
        users = storageManager.loadUsers()

        if (groups.find { it.name == "default" } == null) {
            storageManager.saveGroup(defaultGroup)
            groups.add(defaultGroup)
            logger.info("Created default group!")
        }
    }
}