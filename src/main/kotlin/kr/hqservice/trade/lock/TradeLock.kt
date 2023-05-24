package kr.hqservice.trade.lock

import kr.hqservice.trade.lock.api.TradeLockAPI
import kr.hqservice.trade.lock.command.TradeLockCommand
import kr.hqservice.trade.lock.config.TradeLockConfig
import kr.hqservice.trade.lock.config.impl.GlobalConfig
import kr.hqservice.trade.lock.handler.GuiHandler
import kr.hqservice.trade.lock.handler.ItemEventHandler
import kr.hqservice.trade.lock.service.TradeLockService
import kr.hqservice.trade.lock.service.impl.TradeLockEventHandlingService
import kr.hqservice.trade.lock.service.impl.TradeLockGuiService
import kr.hqservice.trade.lock.service.impl.TradeLockItemService
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger
import kotlin.reflect.KClass

class TradeLock : JavaPlugin(), TradeLockAPI {
    private val services = mutableSetOf<TradeLockService>()
    private lateinit var tradeLockConfig: TradeLockConfig

    override fun onEnable() {
        TradeLockAPI.setAPI(this)

        tradeLockConfig = GlobalConfig(this)
        tradeLockConfig.read()

        services.add(TradeLockItemService(tradeLockConfig))
        services.add(TradeLockGuiService(tradeLockConfig))
        services.add(TradeLockEventHandlingService(tradeLockConfig))

        server.pluginManager.registerEvents(GuiHandler(getTradeLockService(TradeLockGuiService::class)), this)
        server.pluginManager.registerEvents(ItemEventHandler(getTradeLockService(TradeLockEventHandlingService::class)), this)

        TradeLockCommand(this)
    }

    override fun getAPILogger(): Logger {
        return logger
    }

    override fun getTradeLockConfig(): TradeLockConfig {
        return tradeLockConfig
    }

    override fun <T : TradeLockService> getTradeLockService(kClass: KClass<T>): T {
        return services.filterIsInstance(kClass.java).first()
    }

    override fun runTaskLater(tick: Long, block: () -> Unit) {
        server.scheduler.runTaskLaterAsynchronously(this, block, tick)
    }

    override fun reload() {
        tradeLockConfig.read()
    }
}