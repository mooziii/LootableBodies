package de.polylymer.listener

import de.polylymer.database.LootableBody
import de.polylymer.database.MongoManager
import net.axay.kspigot.data.nbtData
import net.axay.kspigot.event.listen
import net.axay.kspigot.items.meta
import net.axay.kspigot.runnables.task
import org.bukkit.*
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.EulerAngle
import java.util.*

object DeathManager {

    init {
        listen<PlayerDeathEvent> {
            if(!it.entity.world.getGameRuleValue(GameRule.KEEP_INVENTORY)!!) {
                val armorStand = it.entity.world.spawnEntity(it.entity.location.clone().subtract(0.0,1.0,0.0), EntityType.ARMOR_STAND) as ArmorStand
                armorStand.isSwimming = true
                armorStand.setBasePlate(false)
                armorStand.setArms(true)
                armorStand.setRotation(42f,0f)
                for (equipmentSlot in EquipmentSlot.values()) {
                    for (lockType in ArmorStand.LockType.values()) {
                        armorStand.addEquipmentLock(equipmentSlot,lockType)
                    }
                }
                armorStand.bodyPose = EulerAngle(80.0, 0.0, 0.0)
                armorStand.headPose = EulerAngle(68.0, 39.0, 10.0)
                armorStand.rightLegPose = EulerAngle(360.0, 0.0, 0.0)
                armorStand.leftArmPose = EulerAngle(240.0, 329.0, 41.0)
                armorStand.rightArmPose = EulerAngle(261.0, 0.0, 265.0)
                armorStand.isCustomNameVisible = true
                armorStand.customName = it.entity.name
                armorStand.addPotionEffect(PotionEffect(PotionEffectType.FIRE_RESISTANCE, Int.MAX_VALUE, 0, false,false))
                armorStand.addScoreboardTag("isBody:true")
                armorStand.addScoreboardTag("hasLoot:true")
                val stack = ItemStack(Material.PLAYER_HEAD)
                var meta = stack.itemMeta as SkullMeta
                meta.owningPlayer = it.entity
                stack.itemMeta = meta
                armorStand.equipment!!.helmet = stack
                val arrayList = arrayListOf<ItemStack>()
                for (itemStack in it.drops) {
                    arrayList.add(itemStack)
                }
                arrayList.add(ItemStack(Material.ROTTEN_FLESH, 3))
                arrayList.add(ItemStack(Material.BONE, 3))
                it.drops.clear()
                MongoManager.BODIES.insertOne(LootableBody(
                    it.entity.uniqueId.toString(),
                    armorStand.uniqueId.toString(),
                    arrayList
                ))
                stack.type = Material.LEATHER_CHESTPLATE
                val leatherMeta = stack.itemMeta as LeatherArmorMeta
                leatherMeta.setColor(Color.OLIVE)
                stack.itemMeta = leatherMeta
                armorStand.equipment!!.chestplate = stack
                stack.type = Material.LEATHER_LEGGINGS
                stack.itemMeta = leatherMeta
                armorStand.equipment!!.leggings = stack
                it.droppedExp = it.entity.expToLevel
                var i = 0
                val event = it
                task(
                    period = 3,
                    howOften = 60
                ) {
                    event.entity.world.spawnParticle(Particle.ASH, event.entity.location.clone().add(0.0,1.0,0.0),0)
                }
                task(
                    period = 15*20
                ) {
                    if(!armorStand.isDead) {
                        if(Random().nextBoolean()) {
                            armorStand.world.playSound(armorStand.location, Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 0.6f, 0f)
                        } else {
                            armorStand.world.playSound(armorStand.location, Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 0.6f, 10f)
                        }
                        i += 1
                        if(i == 15) {
                            armorStand.equipment!!.helmet = ItemStack(Material.SKELETON_SKULL)
                        }
                    } else {
                        it.cancel()
                    }
                }
                task(
                    delay = 30*60*20
                ) {
                    if(event.entity.isDead) {
                        it.cancel()
                    } else {
                        event.entity.remove()
                    }
                }
            }
        }
    }

}