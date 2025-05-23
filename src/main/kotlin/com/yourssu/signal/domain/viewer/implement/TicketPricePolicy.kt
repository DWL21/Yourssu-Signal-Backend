package com.yourssu.signal.domain.viewer.implement

import com.yourssu.signal.config.properties.PolicyConfigurationProperties
import com.yourssu.signal.domain.common.implement.Uuid
import com.yourssu.signal.domain.profile.implement.ProfileReader
import com.yourssu.signal.domain.verification.implement.domain.VerificationCode
import com.yourssu.signal.infrastructure.Notification
import org.springframework.stereotype.Component

public const val NO_MATCH_TICKET_AMOUNT = 0

@Component
class TicketPricePolicy(
    private val configProperties: PolicyConfigurationProperties,
    private val profileReader: ProfileReader,
    private val viewerReader: ViewerReader,
    private val verificationReader: VerificationReader,
) {
    companion object {
        private const val PRICE_DELIMITER = "."
        private const val KEY_VALUE_DELIMITER = "n"
    }

    private val priceToTicketMap: Map<Int, Int> = initializeMap()
    private val priceToTicketRegisteredMap: Map<Int, Int> = initializeRegisteredMap()

    private fun initializeMap(): Map<Int, Int> {
        return configProperties.ticketPricePolicy
            .split(PRICE_DELIMITER)
            .map { it.split(KEY_VALUE_DELIMITER) }
            .associate { it[NO_MATCH_TICKET_AMOUNT].toInt() to it[1].toInt() }
    }

    private fun initializeRegisteredMap(): Map<Int, Int> {
        return configProperties.ticketPriceRegisteredPolicy
            .split(PRICE_DELIMITER)
            .map { it.split(KEY_VALUE_DELIMITER) }
            .associate { it[NO_MATCH_TICKET_AMOUNT].toInt() to it[1].toInt() }
    }

    fun calculateTicketQuantity(price: Int, code: VerificationCode): Int {
        val uuid = verificationReader.findByCode(code).uuid
        if (isFirstPurchasedTicket(uuid)) {
            return priceToTicketRegisteredMap[price]
            ?: priceToTicketMap[price]
            ?: NO_MATCH_TICKET_AMOUNT
        }
        notifyWhenRegisteredTicketQuantity(price, code)
        return priceToTicketMap[price] ?: NO_MATCH_TICKET_AMOUNT
    }

    private fun notifyWhenRegisteredTicketQuantity(
        price: Int,
        code: VerificationCode
    ) {
        if (priceToTicketRegisteredMap[price] != null) {
            Notification.notifyNoFirstPurchasedTicket(price, code)
        }
    }

    private fun isFirstPurchasedTicket(uuid: Uuid): Boolean {
        return profileReader.existsByUuid(uuid) && !viewerReader.existsByUuid(uuid)
    }
}
