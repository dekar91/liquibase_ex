package ru.team42.analyzer.testControllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import ru.team42.analyzer.dto.response.IntegrationDto;
import ru.team42.analyzer.entities.IntegrationEntity;
import ru.team42.analyzer.entities.UserEntity;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.repositories.IntegrationRepository;
import ru.team42.analyzer.repositories.UserRepository;
import ru.team42.analyzer.testsupport.MockMvcBase;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class IntegrationControllerTest extends MockMvcBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IntegrationRepository integrationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity mockedUser;

    @BeforeEach
    void mockUser() {
        UserEntity entity = new UserEntity();
        entity.setUsername("test123");
        entity.setPassword("test123");

        this.mockedUser = userRepository.save(entity);
    }

    @AfterEach
    void removeMockedUser() {

        if (mockedUser != null && mockedUser.getId() != null) {
            userRepository.delete(mockedUser);
        }

    }

    @Test
    public void getList() throws Exception {

        this.mockMvc.perform(get("/integration/list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void getEntity() throws Exception {
        IntegrationEntity entity = new IntegrationEntity();
        entity.setName("test");

        IntegrationEntity savedEntity = integrationRepository.save(entity);

        var result = this.mockMvc.perform(get("/integration/" + savedEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<IntegrationDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertNotNull(deserialized.getData());
        assertThat(deserialized.getData().getId() == savedEntity.getId());
        integrationRepository.delete(savedEntity);

    }


    @Test
    public void createEntity() throws Exception {
        IntegrationDto dto = new IntegrationDto(null, "test create" + mockedUser.getId());

        ObjectMapper o = new ObjectMapper();
        String j = o.writeValueAsString(dto);

        var result = this.mockMvc.perform(post("/integration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(j)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<IntegrationDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        IntegrationEntity entity = integrationRepository.findById(Objects.requireNonNull(deserialized.getData().getId())).orElse(null);
        assertNotNull(entity);
        assertEquals(entity.getName(), dto.getName());

        integrationRepository.delete(entity);
    }

    @Test
    public void updateEntity() throws Exception {

        IntegrationEntity entity = new IntegrationEntity();
        entity.setName("test" + mockedUser.getId());
        entity = integrationRepository.save(entity);

        String newName = entity.getName() + mockedUser.getId();
        IntegrationDto dto = new IntegrationDto(entity.getId(), newName);

        String j = objectMapper.writeValueAsString(dto);
        var result = this.mockMvc.perform(put("/integration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(j)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<IntegrationDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(deserialized.getData().getName(), newName);

        integrationRepository.delete(entity);

    }


    @Test
    @Ignore
    public void deleteEntity() throws Exception {
        IntegrationEntity entity = new IntegrationEntity();
        entity.setName("Test delete");
        entity = integrationRepository.save(entity);

        this.mockMvc.perform(delete("/integration/" + entity.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNull(integrationRepository.findById(entity.getId()).orElse(null));
    }
}
