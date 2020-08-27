package ru.team42.analyzer

import ru.team42.analyzer.testsupport.MockMvcBase
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class DocumentationTest : MockMvcBase() {

    @Test
    fun docsForwarding() {
        mockMvc.perform(get("/docs"))
            .andExpect(status().isOk)
            .andExpect(forwardedUrl("/docs/index.html"))
    }
}
