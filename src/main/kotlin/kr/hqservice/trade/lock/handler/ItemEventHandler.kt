package kr.hqservice.trade.lock.handler

import kr.hqservice.trade.lock.service.impl.TradeLockEventHandlingService
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.player.PlayerDropItemEvent

class ItemEventHandler(
    private val eventHandlingService: TradeLockEventHandlingService
) : Listener {
    @EventHandler(ignoreCancelled = true)
    fun dropItem(event: PlayerDropItemEvent) {
        if(event.player.hasPermission("hqservice.trade.lock.bypass")) return
        eventHandlingService.dropItem(event)
    }

    @EventHandler(ignoreCancelled = true)
    fun pickupItem(event: EntityPickupItemEvent) {
        val entity = event.entity
        if(entity !is Player) return

        if(entity.hasPermission("hqservice.trade.lock.bypass")) return
        eventHandlingService.pickupItem(event)
    }
}