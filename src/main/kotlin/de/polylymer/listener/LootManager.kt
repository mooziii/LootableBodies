package de.polylymer.listener

import de.polylymer.database.LootableBody
import de.polylymer.database.MongoManager
import net.axay.kspigot.event.listen
import org.bukkit.Sound
import org.bukkit.entity.ArmorStand
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.litote.kmongo.findOne

object LootManager {

    init {
        listen<PlayerInteractAtEntityEvent> {
            if(it.rightClicked is ArmorStand) {
                if (it.rightClicked.scoreboardTags.contains("isBody:true")) {
                    it.player.world.playSound(it.player.location, Sound.ENTITY_LEASH_KNOT_BREAK, 1f,1f)
                    val lootableBody = MongoManager.BODIES.findOne("{\"uuid\":\"${it.rightClicked.uniqueId.toString()}\"}")!!
                    for (itemStack in lootableBody.loot) {
                        it.rightClicked.world.dropItem(it.rightClicked.location, itemStack)
                    }
                }
            }
        }
    }

}