package de.polylymer.listener

import de.polylymer.database.Gravestone
import de.polylymer.database.LootableBody
import de.polylymer.database.MongoManager
import net.axay.kspigot.data.nbtData
import net.axay.kspigot.event.listen
import net.axay.kspigot.items.meta
import net.axay.kspigot.runnables.task
import net.axay.kspigot.utils.*
import org.bukkit.*
import org.bukkit.Registry.MATERIAL
import org.bukkit.block.Sign
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.metadata.MetadataValue
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.EulerAngle
import java.text.SimpleDateFormat
import java.util.*

object DeathManager {

    init {
        listen<PlayerDeathEvent> {
            if(!it.entity.world.getGameRuleValue(GameRule.KEEP_INVENTORY)!!) {
                val arrayList = arrayListOf<ItemStack>()
                for (itemStack in it.drops) {
                    arrayList.add(itemStack)
                }
                arrayList.add(ItemStack(Material.ROTTEN_FLESH, 3))
                arrayList.add(ItemStack(Material.BONE, 3))
                it.entity.location.block.type = Material.DARK_OAK_SIGN
                val sign = it.entity.location.block.state as Sign
                val id = Random().nextInt(500000)
                sign.setLine(0, "R.I.P")
                sign.setLine(1, it.entity.name)
                sign.setLine(2, SimpleDateFormat("hh:mm, dd.MM.yyyy").format(Date()))
                sign.setLine(3, id.toString())
                sign.update()
                sign.mark("isGravestone")
                it.drops.clear()
                MongoManager.STONES.insertOne(
                    Gravestone(
                        id.toString(),
                        it.entity.uniqueId.toString(),
                        sign.location,
                        arrayList
                    )
                )
            }
        }
    }

}