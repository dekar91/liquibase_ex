package ru.team42.analyzer.dto.response

import ru.team42.analyzer.dto.ExternalIntegrationConfigInterface
import ru.team42.analyzer.dto.MessengerType
import java.util.*

/**
 * Button setting response for external api
 */
data class ButtonApiSettingsResponse (
        /**
         * Button id
         */
        val buttonId:String,

        /**
         * Messenger Type
         */
        val messengerType:MessengerType,

        /**
         * Url to be passed in href or onCLick
         */
        val url:String,

        /**
         * External configurations
         */
        val integrationConfigurations:List<ExternalIntegrationConfigInterface> = Collections.emptyList()
);