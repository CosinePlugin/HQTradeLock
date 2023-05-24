package kr.hqservice.trade.lock.extension

import kr.ms.core.version.nms.tank.NmsItemStackUtil
import org.bukkit.inventory.ItemStack
import kotlin.reflect.KClass

internal val util by lazy { NmsItemStackUtil.getInstance()!! }

internal inline fun <reified T> ItemStack.addNBTTagCompound(obj: T) {
    val nms = util.asNMSCopy(this)!!
    val tag = nms.tag ?: util.nbtCompoundUtil.newInstance()
    tag.setObject(obj, T::class.java)
    nms.tag = tag
    itemMeta = util.asBukkitCopy(nms).itemMeta
}

internal inline fun <reified T : Any> ItemStack.getNBTTagCompound(clazz: KClass<T>): T? {
    val nms = util.asNMSCopy(this)!!
    val tag = nms.tag ?: return null
    return tag.getObject(clazz.java) ?: return null
}