package dev.aquestry.obelisk.model

data class Group(
    val name: String,
    val permissions: Set<Permission>,
    val config: Config,
    val weight: Int
)