package ru.team42.analyzer.dto.response

import java.util.*

data class ButtonDto (
        val id: Long?,
        val name :String?,
        val channelId: Long? = null,
        val messengerId: Long? = null
)