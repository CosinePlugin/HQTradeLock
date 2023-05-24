package kr.hqservice.trade.lock.service.impl

import kr.hqservice.trade.lock.config.TradeLockConfig
import kr.hqservice.trade.lock.config.impl.section.GeneralSection
import kr.hqservice.trade.lock.enum.Mode
import kr.hqservice.trade.lock.enum.ServiceResult
import kr.hqservice.trade.lock.extension.*
import kr.hqservice.trade.lock.extension.addNBTTagCompound
import kr.hqservice.trade.lock.extension.containsLore
import kr.hqservice.trade.lock.extension.getNBTTagCompound
import kr.hqservice.trade.lock.extension.setLore
import kr.hqservice.trade.lock.nms.TradeLockNBTTagData
import kr.hqservice.trade.lock.service.TradeLockService
import org.bukkit.inventory.ItemStack

class TradeLockItemService(
    config: TradeLockConfig
) : TradeLockService {
    private val generalSection = config.getSection(GeneralSection::class)

    fun setTradeLockItem(itemStack: ItemStack): ServiceResult {
        return when(generalSection.mode) {
            Mode.NBT_TAG -> if(itemStack.getNBTTagCompound(TradeLockNBTTagData::class) != null) {
                val prevData = itemStack.getNBTTagCompound(TradeLockNBTTagData::class)!!
                if(prevData.unchecked) {
                    prevData.unchecked = false
                    ServiceResult.SUCCESS
                } else ServiceResult.FAILED
            }
            else {
                itemStack.addNBTTagCompound(TradeLockNBTTagData(generalSection.text))
                ServiceResult.SUCCESS
            }
            Mode.LORE -> if(itemStack.containsLore(generalSection.text)) ServiceResult.FAILED
            else {
                itemStack.setLore(generalSection.text, generalSection.offset)
                ServiceResult.SUCCESS
            }
            Mode.NAME -> if(itemStack.containsDisplayName(generalSection.text)) ServiceResult.FAILED
            else {
                itemStack.setDisplayName(generalSection.text, generalSection.offset)
                ServiceResult.SUCCESS
            }
        }
    }

    fun unsetTradeLockItem(itemStack: ItemStack): ServiceResult {
        if(!isTradeLockItem(itemStack)) ServiceResult.FAILED
        when(generalSection.mode) {
            Mode.NBT_TAG -> itemStack.addNBTTagCompound(itemStack.getNBTTagCompound(TradeLockNBTTagData::class)?.apply {
                unchecked = true
            })
            Mode.LORE -> itemStack.removeLore(generalSection.text)
            Mode.NAME -> itemStack.removeDisplayName(generalSection.text)
        }
        return ServiceResult.SUCCESS
    }

    fun isTradeLockItem(itemStack: ItemStack): Boolean {
        return when(generalSection.mode) {
            Mode.NBT_TAG -> if(itemStack.getNBTTagCompound(TradeLockNBTTagData::class) != null) {
                !itemStack.getNBTTagCompound(TradeLockNBTTagData::class)!!.unchecked
            } else false
            Mode.LORE -> itemStack.containsLore(generalSection.text)
            Mode.NAME -> itemStack.containsDisplayName(generalSection.text)
        }
    }
}