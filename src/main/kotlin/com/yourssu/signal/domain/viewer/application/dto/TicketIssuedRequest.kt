package com.yourssu.signal.domain.viewer.application.dto

import com.yourssu.signal.domain.viewer.business.command.TicketIssuedCommand
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class TicketIssuedRequest(
    @field:NotBlank
    val secretKey: String,

    @field:NotNull
    val verificationCode: Int,

    @field:Positive
    val ticket: Int,
) {
    fun toCommand(): TicketIssuedCommand {
        return TicketIssuedCommand(
            secretKey = secretKey,
            verificationCode = verificationCode,
            ticket = ticket,
        )
    }
}
