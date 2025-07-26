package dev.aquestry.obelisk.model

import dev.aquestry.obelisk.core.Cache
import java.util.UUID

data class User(
    val uuid: UUID,
    val name: String,
    val groups: MutableSet<Group>,
    val overwriteConfig: Config? = null,
) {
    val config: Config
        get() {
            val highestGroup = groups.maxByOrNull { it.weight }
                ?: Cache.defaultGroup

            val base = highestGroup.config
            return Config(
                permissions = overwriteConfig?.permissions ?: base.permissions,
                prefix = overwriteConfig?.prefix ?: base.prefix,
                suffixTags = overwriteConfig?.suffixTags ?: base.suffixTags,
                nametagFormat = overwriteConfig?.nametagFormat ?: base.nametagFormat,
                messageFormat = overwriteConfig?.messageFormat ?: base.messageFormat
            )
        }
}