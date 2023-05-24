package kr.hqservice.trade.lock.config.impl.section

import kr.hqservice.trade.lock.config.TradeLockSection
import kr.hqservice.trade.lock.extension.toColoredString
import org.bukkit.configuration.ConfigurationSection

class EventsSection : TradeLockSection {
    var dropEnable = true
        private set
    var dropMessage = "§c거래불가 아이템(§e%item%§c)은 버릴 수 없습니다."
        private set

    var pickupEnable = true
        private set
    var pickupMessage = "§c거래불가 아이템(§e%item%§c)은 주울 수 없습니다."
        private set

    override fun read(section: ConfigurationSection) {
        dropEnable = section.getBoolean("drop.cancel")
        dropMessage = section.getString("drop.cancel-message", "§c거래불가 아이템(§e%item%§c)은 버릴 수 없습니다.")!!.toColoredString()

        pickupEnable = section.getBoolean("pickup.cancel")
        pickupMessage = section.getString("pickup.cancel-message", "§c거래불가 아이템(§e%item%§c)은 주울 수 없습니다.")!!.toColoredString()
    }
}