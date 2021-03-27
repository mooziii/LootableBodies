package de.polylymer.listener

import de.polylymer.database.MongoManager
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.utils.hasMark
import org.bukkit.Material
import org.bukkit.block.Sign
import org.bukkit.event.block.BlockBreakEvent
import org.litote.kmongo.bson
import org.litote.kmongo.findOne

object LootManager {

    init {
        listen<BlockBreakEvent> {
            if(it.block.type == Material.DARK_OAK_SIGN) {
                if((it.block.state as Sign).getLine(0) == "R.I.P") {
                    val graveStone = MongoManager.STONES.findOne("{\"id\":\"${(it.block.state as Sign).getLine(3)}\"}")!!
                    for (itemStack in graveStone.loot) {
                        it.block.world.dropItem(it.block.location, itemStack)
                    }
                    MongoManager.STONES.deleteOne("{\"id\":\"${(it.block.state as Sign).getLine(3)}\"}".bson)
                }
            }
        }
    }

}