package dev.aquestry.obelisk.model

import java.util.UUID

data class User(
    val uuid: UUID,
    val name: String,
    val groups: MutableSet<Group>,
    val overrideConfig: Config? = null
) {
    val config: Config
        get() {
            val highestGroup = groups.maxByOrNull { it.weight }
                ?: throw IllegalStateException("User has no groups")

            val base = highestGroup.config
            return Config(
                prefix = overrideConfig?.prefix ?: base.prefix,
                suffixTags = overrideConfig?.suffixTags ?: base.suffixTags,
                nametagFormat = overrideConfig?.nametagFormat ?: base.nametagFormat,
                messageFormat = overrideConfig?.messageFormat ?: base.messageFormat
            )
        }
}