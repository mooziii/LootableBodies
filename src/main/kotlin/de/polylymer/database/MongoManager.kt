package de.polylymer.database

import de.polylymer.config.Config
import net.axay.blueutils.database.mongodb.MongoDB

object MongoManager {

    val mongoDB = MongoDB(Config.dbInfo, spigot = true)

    val BODIES = mongoDB.getCollectionOrCreate<LootableBody>("LOOTABLEBODIES_BODIES")

}