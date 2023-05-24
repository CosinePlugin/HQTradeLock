package kr.hqservice.trade.lock.handler

import kr.hqservice.trade.lock.service.impl.TradeLockGuiService
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class GuiHandler(
    private val guiService: TradeLockGuiService
) : Listener {
    @EventHandler(ignoreCancelled = true)
    fun clickedInventory(event: InventoryClickEvent) {
        if(event.whoClicked.hasPermission("hqservice.trade.lock.bypass")) return
        guiService.clickedInventory(event)
    }
}