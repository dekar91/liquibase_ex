package ru.team42.analyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApiControllerTest {

    @LocalServerPort
    private int port;

//    @Autowired
//    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Autowired
    private MockMvc mockMvc;

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
