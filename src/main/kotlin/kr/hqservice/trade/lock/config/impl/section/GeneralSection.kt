package kr.hqservice.trade.lock.config.impl.section

import kr.hqservice.trade.lock.api.TradeLockAPI
import kr.hqservice.trade.lock.config.TradeLockSection
import kr.hqservice.trade.lock.enum.Mode
import kr.hqservice.trade.lock.enum.Offset
import kr.hqservice.trade.lock.extension.toColoredString
import org.bukkit.configuration.ConfigurationSection

class GeneralSection : TradeLockSection {
    private val logger by lazy { TradeLockAPI.instance.getAPILogger() }

    var mode: Mode = Mode.NBT_TAG
        private set
    var text: String = "§c[ 거래 불가 ]"
        private set
    var offset: Offset = Offset.LAST
        private set

    override fun read(section: ConfigurationSection) {
        mode =
            try { Mode.valueOf(section.getString("mode")!!.uppercase()) }
            catch (_: Exception) {
                logger.severe("config.yml 의 general.mode 값이 올바르지 않습니다.\n 기본 값인 mode: nbt 로 설정됩니다.")
                Mode.NBT_TAG
            }

        text = section.getString("text")?.toColoredString()?: "§c[ 거래 불가 ]"
        offset =
            try { Offset.valueOf(section.getString("offset")!!.uppercase()) }
            catch (_: Exception) {
                logger.severe("config.yml 의 general.offset 값이 올바르지 않습니다.\n 기본 값인 offset: last 로 설정됩니다.")
                Offset.LAST
            }
    }
}