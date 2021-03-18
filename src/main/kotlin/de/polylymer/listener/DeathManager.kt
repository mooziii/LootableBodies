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
import org.bukkit.util.EulerAngle

object DeathManager {

    init {
        listen<PlayerDeathEvent> {
            if(!it.entity.world.getGameRuleValue(GameRule.KEEP_INVENTORY)!!) {
                val armorStand = it.entity.world.spawnEntity(it.entity.location.clone().subtract(0.0,1.0,0.0), EntityType.ARMOR_STAND) as ArmorStand
                armorStand.isSwimming = true
                armorStand.setBasePlate(false)
                armorStand.setArms(true)
                armorStand.setRotation(42f,0f)
                armorStand.bodyPose = EulerAngle(80.0, 0.0, 0.0)
                armorStand.headPose = EulerAngle(68.0, 39.0, 10.0)
                armorStand.rightLegPose = EulerAngle(360.0, 0.0, 0.0)
                armorStand.leftArmPose = EulerAngle(240.0, 329.0, 41.0)
                armorStand.rightArmPose = EulerAngle(261.0, 0.0, 265.0)
                armorStand.addScoreboardTag("isBody:true")
                armorStand.equipment!!.helmet = ItemStack(Material.SKELETON_SKULL)
                val arrayList = arrayListOf<ItemStack>()
                for (itemStack in it.drops) {
                    arrayList.add(itemStack)
                }
                it.drops.clear()
                MongoManager.BODIES.insertOne(LootableBody(
                    it.entity.uniqueId.toString(),
                    armorStand.uniqueId.toString(),
                    arrayList
                ))
            }
        }
    }

}