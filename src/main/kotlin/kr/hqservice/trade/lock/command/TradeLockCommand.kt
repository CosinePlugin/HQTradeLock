package kr.hqservice.trade.lock.command

import kr.hqservice.trade.lock.api.TradeLockAPI
import kr.hqservice.trade.lock.enum.ServiceResult
import kr.hqservice.trade.lock.service.impl.TradeLockItemService
import kr.ms.core.annotation.Subcommand
import kr.ms.core.command.MSCommand
import kr.ms.core.command.wrapper.CommandSenderWrapper
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin

class TradeLockCommand(
    plugin: JavaPlugin
) : MSCommand("거래불가", "거래불가 관련 명령어", plugin) {
    private val itemService = TradeLockAPI.instance.getTradeLockService(TradeLockItemService::class)

    @Subcommand(subCommand = "설정", description = "손에 든 아이템을 거래불가 아이템으로 설정합니다.")
    fun setTradeLockItem(sender: CommandSenderWrapper) {
        val item = sender.player.inventory.itemInMainHand?: return
        if(item.type == Material.AIR) return

        if(itemService.setTradeLockItem(item) == ServiceResult.SUCCESS) {
            sender.sendMessage("§7손에 든 아이템을 거래불가 아이템으로 지정했습니다.")
        } else {
            sender.sendMessage("§7손에 든 아이템은 이미 거래불가 아이템입니다.")
        }
    }

    @Subcommand(subCommand = "해제", description = "손에 든 아이템을 거래불가 아이템에서 해제합니다.")
    fun unsetTradeLockItem(sender: CommandSenderWrapper) {
        val item = sender.player.inventory.itemInMainHand?: return
        if(item.type == Material.AIR) return

        if(itemService.unsetTradeLockItem(item) == ServiceResult.SUCCESS) {
            sender.sendMessage("§7손에 든 아이템을 거래불가 아이템에서 해제했습니다.")
        } else {
            sender.sendMessage("§7손에 든 아이템은 이미 거래불가 아이템이 아닙니다.")
        }
    }

    @Subcommand(subCommand = "체크", description = "손에 든 아이템이 거래불가 아이템인지 확인합니다.")
    fun checkTradeLockItem(sender: CommandSenderWrapper) {
        val item = sender.player.inventory.itemInMainHand?: return
        if(item.type == Material.AIR) return

        if(itemService.isTradeLockItem(item)) {
            sender.sendMessage("§7손에 든 아이템은 거래불가 아이템입니다.")
        } else {
            sender.sendMessage("§7손에 든 아이템은 거래불가 아이템이 아닙니다.")
        }
    }

    @Subcommand(subCommand = "리로드", description = "config.yml 파일을 새로 읽어옵니다.")
    fun reloadConfig(sender: CommandSenderWrapper) {
        TradeLockAPI.instance.reload()
        sender.sendMessage("§aconfig.yml 파일을 새로 읽어왔습니다.")
    }
}