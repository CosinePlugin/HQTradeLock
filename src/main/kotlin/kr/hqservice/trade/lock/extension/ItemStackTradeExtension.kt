package kr.hqservice.trade.lock.extension

import kr.hqservice.trade.lock.enum.Offset
import kr.ms.core.util.ItemStackNameUtil
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

internal fun ItemStack.containsLore(text: String): Boolean {
    return itemMeta?.lore?.firstOrNull { it.contains(text) } != null
}

internal fun ItemStack.setLore(text: String, offset: Offset) {
    val meta = itemMeta!!
    val lore = meta.lore?: mutableListOf()
    if(offset == Offset.FIRST) {
        lore.add(0, text)
    } else {
        lore.add(text)
    }
    meta.lore = lore
    itemMeta = meta
}

internal fun ItemStack.removeLore(text: String) {
    val meta = itemMeta!!
    val lore = meta.lore?: return
    lore.removeIf { it.contains(text) }
    meta.lore = lore
    itemMeta = meta
}

internal fun ItemStack.containsDisplayName(text: String): Boolean {
    return itemMeta?.displayName?.contains(text) == true
}

internal fun ItemStack.setDisplayName(text: String, offset: Offset) {
    val meta = itemMeta!!
    var name = ItemStackNameUtil.getItemName(this)
    if(offset == Offset.FIRST) {
        name = text + name
    } else {
        name += text
    }
    meta.setDisplayName(name)
    itemMeta = meta
}

internal fun ItemStack.removeDisplayName(text: String) {
    val meta = itemMeta!!
    val displayName = meta.displayName?: return
    if(displayName.isEmpty()) return
    meta.setDisplayName(displayName.replace(text, ""))
    itemMeta = meta
}