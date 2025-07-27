package dev.aquestry.obelisk.core

import dev.aquestry.obelisk.Obelisk.storageManager
import dev.aquestry.obelisk.model.Config
import dev.aquestry.obelisk.model.Group
import dev.aquestry.obelisk.model.User
import kotlinx.coroutines.runBlocking

object Cache {
    var groups = mutableSetOf<Group>()
    var users = mutableSetOf<User>()

    val fallbackGroup = Group(
        "fallback",
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
    }
}