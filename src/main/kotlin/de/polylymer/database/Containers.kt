@file:UseSerializers(ItemStackSerializer::class)
package de.polylymer.database

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import net.axay.kspigot.serialization.ItemStackSerializer
import org.bukkit.inventory.ItemStack

@Serializable
data class LootableBody(val owner: String, val uuid: String, var loot: ArrayList<ItemStack>)
