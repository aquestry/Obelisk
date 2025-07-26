package dev.aquestry.obelisk.core

import dev.aquestry.obelisk.model.Permission
import net.minestom.server.entity.Player

@Suppress("unused")
object EventManager {
    fun Player.hasPermission(permission: String): Boolean {
        val permissions = Cache.users.find { it.uuid == this.uuid }?.config?.permissions
            ?: Cache.groups.find { it.name == "default" }?.config?.permissions
            ?: emptySet()

        return permissions.contains(Permission(permission, true))
    }
}