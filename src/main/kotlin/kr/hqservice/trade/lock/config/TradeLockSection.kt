package kr.hqservice.trade.lock.config

import org.bukkit.configuration.ConfigurationSection

interface TradeLockSection {

    fun read(section: ConfigurationSection)

}