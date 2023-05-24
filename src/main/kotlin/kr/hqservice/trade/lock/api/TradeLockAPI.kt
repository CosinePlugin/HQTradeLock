package kr.hqservice.trade.lock.api

import kr.hqservice.trade.lock.config.TradeLockConfig
import kr.hqservice.trade.lock.service.TradeLockService
import org.bukkit.scheduler.BukkitScheduler
import java.util.logging.Logger
import kotlin.reflect.KClass

interface TradeLockAPI {
    companion object {
        lateinit var instance: TradeLockAPI
            private set

        fun setAPI(api: TradeLockAPI) {
            this.instance = api
        }
    }

    fun getAPILogger(): Logger

    fun getTradeLockConfig(): TradeLockConfig

    fun <T: TradeLockService> getTradeLockService(kClass: KClass<T>): T

    fun runTaskLater(tick: Long, block: ()-> Unit)

    fun reload()
}