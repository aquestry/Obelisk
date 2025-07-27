package dev.aquestry.obelisk.core

import dev.aquestry.obelisk.Obelisk.settings
import dev.aquestry.obelisk.model.Config
import dev.aquestry.obelisk.model.Permission
import net.kyori.adventure.text.minimessage.MiniMessage.miniMessage
import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerChatEvent
import net.minestom.server.event.player.PlayerSpawnEvent

@Suppress("unused")
object EventManager {
    fun register() {
        val eventHandler = MinecraftServer.getGlobalEventHandler()

        if(settings.chatFormatting) {
            eventHandler.addListener(PlayerChatEvent::class.java) { event ->
                event.isCancelled = true

                val config = event.player.getConfig()
                val message = config.messageFormat
                    .replace("<username>", event.player.username)
                    .replace("<prefix>", config.prefix)
                    .replace("<suffixTags>", config.suffixTags.joinToString(""))
                    .replace("<messageRaw>", miniMessage().escapeTags(event.rawMessage))
                    .replace("messageFormatted", event.rawMessage)

                event.recipients.forEach { it.sendMessage(miniMessage().deserialize(message)) }
            }
        }

        if(settings.nameFormatting) {
            eventHandler.addListener(PlayerSpawnEvent::class.java) { event ->
                val config = event.player.getConfig()

                event.player.displayName = miniMessage().deserialize(
                    config.nameFormat
                    .replace("<username>", event.player.username)
                    .replace("<prefix>", config.prefix)
                    .replace("<suffixTags>", config.suffixTags.joinToString()
                    .trim()
                    )
                )
            }
        }
    }

    fun Player.getConfig(): Config {
        return Cache.users.find { it.uuid == this.uuid }?.config
            ?: Cache.groups.find { it.name == "default" }?.config
            ?: Cache.fallbackGroup.config
    }

    fun Player.hasPermission(permission: String): Boolean {
        val permissions = Cache.users.find { it.uuid == this.uuid }?.config?.permissions
            ?: Cache.groups.find { it.name == "default" }?.config?.permissions
            ?: emptySet()

        return permissions.contains(Permission(permission, true))
    }
}