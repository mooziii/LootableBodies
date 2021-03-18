package de.polylymer

import de.polylymer.database.MongoManager
import de.polylymer.listener.DeathManager
import de.polylymer.listener.LootManager
import net.axay.kspigot.main.KSpigot

class LootableBodies : KSpigot() {

    lateinit var mongoManager: MongoManager

    override fun startup() {
        mongoManager = MongoManager
        DeathManager
        LootManager
    }

}