package kr.hqservice.trade.lock.extension

import org.bukkit.ChatColor

fun String.toColoredString(): String {
    return ChatColor.translateAlternateColorCodes('&', this)
}