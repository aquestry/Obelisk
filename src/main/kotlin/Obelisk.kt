package dev.aquestry.obelisk

import dev.aquestry.obelisk.core.Cache
import dev.aquestry.obelisk.model.Settings
import dev.aquestry.obelisk.storage.LocalSM
import dev.aquestry.obelisk.storage.StorageManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object Obelisk {
    lateinit var storageManager: StorageManager
    lateinit var logger: Logger
    var settings = Settings()

    fun init() {
        logger = LoggerFactory.getLogger("Obelisk")
        logger.info("Obelisk is initializing...")

        if(!::storageManager.isInitialized) {
            storageManager = LocalSM()
            logger.info("Using local storage manager!")
        }

        Cache.load()

        logger.info("Obelisk is initialized!")
    }
}