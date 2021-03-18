package de.polylymer.listener

import de.polylymer.database.LootableBody
import de.polylymer.database.MongoManager
import net.axay.kspigot.event.listen
import org.bukkit.Sound
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.litote.kmongo.bson
import org.litote.kmongo.findOne

object LootManager {

    init {
        listen<PlayerInteractAtEntityEvent> {
            if(it.rightClicked is ArmorStand) {
                if (it.rightClicked.scoreboardTags.contains("hasLoot:true")) {
                    it.player.world.playSound(it.player.location, Sound.ENTITY_LEASH_KNOT_BREAK, 1f,1f)
                    val lootableBody = MongoManager.BODIES.findOne("{\"uuid\":\"${it.rightClicked.uniqueId.toString()}\"}")!!
                    for (itemStack in lootableBody.loot) {
                        it.rightClicked.world.dropItem(it.rightClicked.location, itemStack)
                    }
                    it.rightClicked.removeScoreboardTag("hasLoot:true")
                    MongoManager.BODIES.deleteOne("{\"uuid\":\"${it.rightClicked.uniqueId.toString()}\"}".bson)
                }
            }
        }
        listen<EntityDamageByEntityEvent> {
            if(it.entity is ArmorStand) {
                if(it.entity.scoreboardTags.contains("isBody:true")) {
                    if(it.damager is Player) {
                        if((it.damager as Player).inventory.itemInMainHand.type.name.contains("SHOVEL")) {
                            it.entity.remove()
                        } else {
                            it.isCancelled = true
                        }
                    }
                }
            }
        }
    }

}