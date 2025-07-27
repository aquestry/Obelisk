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
                val template = config.messageFormat
                    .replace("<username>", event.player.username)
                    .replace("<messageRaw>", miniMessage().escapeTags(event.rawMessage))

                val finalComponent = miniMessage().deserialize(template)
                    .replaceText { it.matchLiteral("<messageFormatted>").replacement(miniMessage().deserialize(event.rawMessage)) }
                    .replaceText { it.matchLiteral("<prefix>").replacement(miniMessage().deserialize(config.prefix)) }
                    .replaceText { it.matchLiteral("<suffixTags>").replacement(miniMessage().deserialize(config.suffixTags.joinToString())) }
                event.recipients.forEach { it.sendMessage(finalComponent) }
            }
        }

        if(settings.nameFormatting) {
            eventHandler.addListener(PlayerSpawnEvent::class.java) { event ->
                val config = event.player.getConfig()
                val template = config.messageFormat
                    .replace("<username>", event.player.username)

                val finalComponent = miniMessage().deserialize(template)
                    .replaceText { it.matchLiteral("<prefix>").replacement(miniMessage().deserialize(config.prefix)) }
                    .replaceText { it.matchLiteral("<suffixTags>").replacement(miniMessage().deserialize(config.suffixTags.joinToString())) }

                event.player.displayName = finalComponent
            }
        }
    }

    fun Player.getConfig(): Config {
        return Cache.users.find { it.uuid == this.uuid }?.config
            ?: Cache.groups.find { it.name == "default" }?.config
            ?: Cache.defaultGroup.config
    }

    fun Player.hasPermission(permission: String): Boolean {
        val permissions = Cache.users.find { it.uuid == this.uuid }?.config?.permissions
            ?: Cache.groups.find { it.name == "default" }?.config?.permissions
            ?: emptySet()

        return permissions.contains(Permission(permission, true))
    }
}