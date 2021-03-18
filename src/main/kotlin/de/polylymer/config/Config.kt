package de.polylymer.config

import net.axay.blueutils.database.DatabaseLoginInformation
import net.axay.kspigot.config.PluginFile
import net.axay.kspigot.config.kSpigotJsonConfig

object Config {

    val dbInfo
            by kSpigotJsonConfig(PluginFile("db.json")) {
                DatabaseLoginInformation.NOTSET_DEFAULT
            }

}