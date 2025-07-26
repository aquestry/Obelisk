import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.instance.LightingChunk
import net.minestom.server.instance.block.Block
import net.minestom.server.instance.Instance
import org.slf4j.Logger
import org.slf4j.LoggerFactory

lateinit var logger: Logger
lateinit var instance: Instance
lateinit var server: MinecraftServer

fun main() {
    logger = LoggerFactory.getLogger("Main")
    server = MinecraftServer.init()

    instance = MinecraftServer.getInstanceManager().createInstanceContainer().apply {
        setChunkSupplier(::LightingChunk)
        setGenerator { it.modifier().fillHeight(-1, 0, Block.GRASS_BLOCK) }
    }

    MinecraftServer.getGlobalEventHandler().addListener(AsyncPlayerConfigurationEvent::class.java) { event ->
        event.player.respawnPoint = Pos(0.0, 0.0, 0.0)
        event.spawningInstance = instance
    }

    server.start("0.0.0.0", 25565)
}