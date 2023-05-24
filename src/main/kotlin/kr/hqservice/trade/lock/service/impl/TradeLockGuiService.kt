package kr.hqservice.trade.lock.service.impl

import kr.hqservice.trade.lock.api.TradeLockAPI
import kr.hqservice.trade.lock.config.TradeLockConfig
import kr.hqservice.trade.lock.config.impl.section.GuiSection
import kr.hqservice.trade.lock.service.TradeLockService
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent

class TradeLockGuiService(
    config: TradeLockConfig
) : TradeLockService {
    private val itemService = TradeLockAPI.instance.getTradeLockService(TradeLockItemService::class)
    private val guiSection = config.getSection(GuiSection::class)

    fun clickedInventory(event: InventoryClickEvent) {
        val current = event.currentItem
        val hotBar = if(event.hotbarButton >= 0) event.whoClicked.inventory.getItem(event.hotbarButton) else null
        val offhand = if(event.click == ClickType.SWAP_OFFHAND) event.whoClicked.inventory.itemInOffHand else null

        if(guiSection.isCheckInventory(event.view)) {
            if(current != null && current.type != Material.AIR) {
                if(itemService.isTradeLockItem(current)) {
                    event.isCancelled = true
                    guiSection.sendCancelMessage(event.whoClicked as Player, current)
                    return
                }
            }

            if(hotBar != null && hotBar.type != Material.AIR) {
                if(itemService.isTradeLockItem(hotBar)) {
                    event.isCancelled = true
                    guiSection.sendCancelMessage(event.whoClicked as Player, hotBar)
                    return
                }
            }

            if(offhand != null && offhand.type != Material.AIR) {
                if(itemService.isTradeLockItem(offhand)) {
                    event.isCancelled = true
                    guiSection.sendCancelMessage(event.whoClicked as Player, offhand)
                    return
                }
            }
        }
    }
}