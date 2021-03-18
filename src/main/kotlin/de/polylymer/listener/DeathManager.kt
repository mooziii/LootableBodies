package de.polylymer.listener

import de.polylymer.database.LootableBody
import de.polylymer.database.MongoManager
import net.axay.kspigot.event.listen
import org.bukkit.GameRule
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack

object DeathManager {

    init {
        listen<PlayerDeathEvent> {
            if(!it.entity.world.getGameRuleValue(GameRule.KEEP_INVENTORY)!!) {
                val armorStand = it.entity.world.spawnEntity(it.entity.location, EntityType.ARMOR_STAND) as ArmorStand
                armorStand.isSwimming = true
                armorStand.addScoreboardTag("isBody:true")
                armorStand.equipment!!.helmet = ItemStack(Material.SKELETON_SKULL)
                val arrayList = arrayListOf<ItemStack>()
                for (itemStack in it.drops) {
                    arrayList.add(itemStack)
                }
                MongoManager.BODIES.insertOne(LootableBody(
                    it.entity.uniqueId.toString(),
                    armorStand.uniqueId.toString(),
                    arrayList
                ))
            }
        }
    }

}