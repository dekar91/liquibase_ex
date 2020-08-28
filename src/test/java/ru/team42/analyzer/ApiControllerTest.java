package ru.team42.analyzer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import ru.team42.analyzer.testsupport.MockMvcBase;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ApiControllerTest extends MockMvcBase {

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/api/hit").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
                .andDo(document("api/hit"));
//        ResultActions resultActions = mockMvc.perform(get("/api/config/channels=entity_206_123"));

//        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/hit",
//                String.class)).isNotEmpty();
    }

    @Test
    public void getConfig() throws Exception {
        this.mockMvc.perform(get("/api/config?channels=entity_206_123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("api/config"
//                        , responseFields(
//                        fieldWithPath("[].js_class"),
//                        fieldWithPath("[].url")
//                )
                ));

//        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/hit",
//                String.class)).isNotEmpty();
    }

}
