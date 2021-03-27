@file:UseSerializers(ItemStackSerializer::class, LocationSerializer::class)
package de.polylymer.database

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import net.axay.kspigot.serialization.ItemStackSerializer
import net.axay.kspigot.serialization.LocationSerializer
import org.bukkit.Location
import org.bukkit.inventory.ItemStack

@Serializable
data class LootableBody(val owner: String, val uuid: String, var loot: ArrayList<ItemStack>)

@Serializable
data class Gravestone(val id: String, val owner: String, val location: Location, var loot: ArrayList<ItemStack>)