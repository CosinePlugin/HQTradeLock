package kr.hqservice.trade.lock.service.impl

import kr.hqservice.trade.lock.api.TradeLockAPI
import kr.hqservice.trade.lock.config.TradeLockConfig
import kr.hqservice.trade.lock.config.impl.section.EventsSection
import kr.hqservice.trade.lock.service.TradeLockService
import kr.ms.core.util.ItemStackNameUtil
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.player.PlayerDropItemEvent
import java.util.UUID

class TradeLockEventHandlingService(
    config: TradeLockConfig
) : TradeLockService {
    private val itemService = TradeLockAPI.instance.getTradeLockService(TradeLockItemService::class)
    private val eventsSection = config.getSection(EventsSection::class)

    private val delaySet = mutableSetOf<UUID>()

    fun dropItem(event: PlayerDropItemEvent) {
        val item = event.itemDrop.itemStack
        if(eventsSection.dropEnable) {
            if(itemService.isTradeLockItem(item)) {
                event.isCancelled = true
                event.player.sendMessage(eventsSection.dropMessage
                    .replace("%item%", ItemStackNameUtil.getItemName(item)))
            }
        }
    }

    fun pickupItem(event: EntityPickupItemEvent) {
        val item = event.item.itemStack
        if(eventsSection.pickupEnable) {
            if(itemService.isTradeLockItem(item)) {
                event.isCancelled = true
                if(delaySet.contains(event.entity.uniqueId)) return
                delaySet.add(event.entity.uniqueId)
                event.entity.sendMessage(eventsSection.pickupMessage
                    .replace("%item%", ItemStackNameUtil.getItemName(item)))
                TradeLockAPI.instance.runTaskLater(20) { delaySet.remove(event.entity.uniqueId) }
            }
        }
    }
}