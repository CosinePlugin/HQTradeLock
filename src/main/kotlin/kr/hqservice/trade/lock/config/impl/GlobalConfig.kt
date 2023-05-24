package kr.hqservice.trade.lock.config.impl

import kr.hqservice.trade.lock.config.TradeLockConfig
import kr.hqservice.trade.lock.config.TradeLockSection
import kr.hqservice.trade.lock.config.impl.section.EventsSection
import kr.hqservice.trade.lock.config.impl.section.GeneralSection
import kr.hqservice.trade.lock.config.impl.section.GuiSection
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import kotlin.reflect.KClass

class GlobalConfig(
    private val plugin: Plugin
) : TradeLockConfig {
    private val sections = mutableMapOf(
        "general" to GeneralSection(),
        "gui" to GuiSection(),
        "events" to EventsSection()
    )

    override fun <T : TradeLockSection> getSection(kClass: KClass<T>): T {
        return sections.values.filterIsInstance(kClass.java).first()
    }

    override fun read() {
        val file = File(plugin.dataFolder, "config.yml")
        if(!file.exists()) plugin.saveDefaultConfig()
        val config = YamlConfiguration.loadConfiguration(file)
        sections.forEach { (key, section) ->
            section.read(
                config.getConfigurationSection(key)?: return@forEach)
        }
    }
}