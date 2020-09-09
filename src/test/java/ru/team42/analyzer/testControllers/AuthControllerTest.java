package ru.team42.analyzer.testControllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import ru.team42.analyzer.dto.request.AuthRequest;
import ru.team42.analyzer.testsupport.MockMvcBase;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AuthControllerTest extends MockMvcBase {

    @Test
    public void testSuccessLogin() throws Exception {
        var dto = new AuthRequest(TEST_USER_LOGIN, TEST_USER_PASSWORD);
        var j = objectMapper.writeValueAsString(dto);

        mockMvc
            .perform(
                    post("/login")
                            .contentType(
                                    MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(j)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.token", notNullValue()));
    }

    @Test
    public void testFailedLogin() throws Exception {
        var dto = new AuthRequest(TEST_USER_LOGIN, TEST_USER_PASSWORD + "123");
        var j = objectMapper.writeValueAsString(dto);

        mockMvc
                .perform(
                        post("/login")
                                .contentType(
                                        MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(j)
                )
                .andExpect(status().isForbidden());
    }
}