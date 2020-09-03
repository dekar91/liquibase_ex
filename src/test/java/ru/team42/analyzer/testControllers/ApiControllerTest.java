package ru.team42.analyzer.testControllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ru.team42.analyzer.dto.MessengerType;
import ru.team42.analyzer.dto.response.ButtonApiSettingsResponse;
import ru.team42.analyzer.repositories.UserRepository;
import ru.team42.analyzer.services.interfaces.ApiService;
import ru.team42.analyzer.testsupport.MockMvcBase;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ApiControllerTest extends MockMvcBase {

    @MockBean
    private ApiService apiService;

    @Autowired
    private UserRepository userRepository;

    List<ButtonApiSettingsResponse> buttonMock() {
        return List.of(
                new ButtonApiSettingsResponse("button_123", MessengerType.TELEGRAM, "http://test.ru", Collections.emptyList())
        );
    }


    @Test
    public void testGetConfiguration() throws Exception {
        doReturn(buttonMock()).when(apiService).getConfig(List.of("button_123"));

        this.mockMvc.perform(get("/api/config?buttonClasses=button_123").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

//                .andExpect(ApiResponse.buildWithData(buttonMock()));
    }

//    @Test
//    public void greetingShouldReturnDefaultMessage() throws Exception {
//        this.mockMvc.perform(get("/api/hit").accept(MediaType.APPLICATION_JSON))
////                .andExpect(status().isOk())
//                .andDo(document("api/hit"));
////        ResultActions resultActions = mockMvc.perform(get("/api/config/channels=entity_206_123"));
//
////        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/hit",
////                String.class)).isNotEmpty();
//    }
//
//    @Test
//    public void getConfig() throws Exception {
//        this.mockMvc.perform(get("/api/config?channels=entity_206_123").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(document("api/config"
////                        , responseFields(
////                        fieldWithPath("[].js_class"),
////                        fieldWithPath("[].url")
////                )
//                ));

//        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/hit",
//                String.class)).isNotEmpty();
//    }

}
