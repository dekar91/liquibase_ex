package ru.team42.analyzer.dto.response

import java.util.*

data class ChannelDto (
        val id: Long? = null,
        val name :String? = "",
        val userId: Long? = null,
        val messengersIds: List<Long> = Collections.emptyList(),
        val buttonsId: List<Long>  = Collections.emptyList()
)