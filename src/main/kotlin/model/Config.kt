package dev.aquestry.obelisk.model

data class Config(
    val permissions: Set<Permission>,
    val prefix: String,
    val nameFormat: String,
    val messageFormat: String,
    val suffixTags: List<String>,
)