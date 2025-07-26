import dev.aquestry.obelisk.Obelisk
import dev.aquestry.obelisk.storage.LocalSM
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

fun main() = runBlocking {
    val logger = LoggerFactory.getLogger("Test")
    val sm = LocalSM()
    sm.loadSettings()
    logger.info(Obelisk.settings!!.nametagSystem.toString())
}