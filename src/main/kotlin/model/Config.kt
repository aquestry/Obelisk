package dev.aquestry.obelisk.model

import net.kyori.adventure.text.Component

data class Config(
    val prefix: Component,
    val suffixTags: List<Component>,
    val nametagFormat: String,
    val messageFormat: String,
)