package de.polylymer

import de.polylymer.database.MongoManager
import net.axay.kspigot.main.KSpigot

class LootableBodies : KSpigot() {

    lateinit var mongoManager: MongoManager

    override fun startup() {
        mongoManager = MongoManager
    }

}