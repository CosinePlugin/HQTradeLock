package kr.hqservice.trade.lock.config

import kotlin.reflect.KClass

interface TradeLockConfig {
    fun <T: TradeLockSection> getSection(kClass: KClass<T>): T

    fun read()
}