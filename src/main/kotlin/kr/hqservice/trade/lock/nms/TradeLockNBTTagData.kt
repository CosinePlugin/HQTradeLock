package kr.hqservice.trade.lock.nms

data class TradeLockNBTTagData(
    private val text: String,
    var unchecked: Boolean = false
)