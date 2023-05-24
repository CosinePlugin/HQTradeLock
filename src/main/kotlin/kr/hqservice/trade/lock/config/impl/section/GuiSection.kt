package kr.hqservice.trade.lock.config.impl.section

import kr.hqservice.trade.lock.api.TradeLockAPI
import kr.hqservice.trade.lock.config.TradeLockSection
import kr.hqservice.trade.lock.extension.toColoredString
import kr.ms.core.util.ItemStackNameUtil
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class GuiSection : TradeLockSection {
    private val logger by lazy { TradeLockAPI.instance.getAPILogger() }

    private var disableTitles: List<String>? = null
    private var cancelMessage: String = "§c거래불가 아이템(§e%item%§c)은 이 창에서 클릭 할 수 없습니다."
    private val bypassTitles = mutableListOf<String>()
    private val disableTypes = mutableListOf<InventoryType>()
    private val bypassTypes = mutableListOf<InventoryType>()

    override fun read(section: ConfigurationSection) {
        disableTitles = if(section.isString("disable-title")) {
            val title = section.getString("disable-title", "*")!!
            if(title == "*") null else listOf(title)
        } else {
            section.getStringList("disable-title").map { it.toColoredString() }
        }

        cancelMessage = section.getString("cancel-message", "§c거래불가 아이템(§e%item%§c)은 이 창에서 클릭 할 수 없습니다.")!!.toColoredString()

        bypassTitles.clear()
        disableTypes.clear()
        bypassTypes.clear()

        section.getStringList("bypass-title").forEach {
            bypassTitles.add(it)
        }

        section.getStringList("disable-type").forEach {
            try {
                disableTypes.add(InventoryType.valueOf(it.uppercase()))
            } catch (_: Exception) {
                logger.severe("gui.disable-type 의 '${it}' 값이 올바르지 않습니다.\n해당 값이 설정되지 않은 상태로 서버가 켜집니다.")
            }
        }

        section.getStringList("bypass-type").forEach {
            try {
                bypassTypes.add(InventoryType.valueOf(it.uppercase()))
            } catch (_: Exception) {
                logger.severe("gui.bypass-type 의 '${it}' 값이 올바르지 않습니다.\n해당 값이 설정되지 않은 상태로 서버가 켜집니다.")
            }
        }
    }

    fun isCheckInventory(inventoryView: InventoryView): Boolean {
        val topType = inventoryView.topInventory.type
        if(bypassTypes.contains(topType))
            return false

        val title = inventoryView.title
        if(bypassTitles.firstOrNull { title.contains(it) } != null)
            return false

        return disableTypes.contains(topType) ||
                disableTitles == null ||
                disableTitles?.firstOrNull { title.contains(it) } != null
    }

    fun sendCancelMessage(player: Player, itemStack: ItemStack) {
        player.sendMessage(
            cancelMessage.replace("%item%", ItemStackNameUtil.getItemName(itemStack))
        )
    }
}