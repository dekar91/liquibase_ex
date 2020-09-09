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
import ru.team42.analyzer.dto.response.MessengerDto;
import ru.team42.analyzer.entities.MessengerEntity;
import ru.team42.analyzer.entities.UserEntity;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.repositories.MessengerRepository;
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
public class MessengerControllerTest extends MockMvcBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessengerRepository messengerRepository;

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

        this.mockMvc.perform(get("/messenger/list").with(userToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void getEntity() throws Exception {
        MessengerEntity entity = new MessengerEntity();
        entity.setUser(mockedUser);
        MessengerEntity savedEntity = messengerRepository.save(entity);

        var result = this.mockMvc.perform(get("/messenger/" + savedEntity.getId()).with(userToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<MessengerDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertNotNull(deserialized.getData());
        assertThat(deserialized.getData().getId() == savedEntity.getId());
        messengerRepository.delete(savedEntity);

    }


    @Test
    public void createEntity() throws Exception {
        MessengerDto dto = new MessengerDto(null, "test create" + mockedUser.getId(), mockedUser.getId());

        ObjectMapper o = new ObjectMapper();
        String j = o.writeValueAsString(dto);

        var result = this.mockMvc.perform(post("/messenger").with(userToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(j)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<MessengerDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        MessengerEntity entity = messengerRepository.findById(Objects.requireNonNull(deserialized.getData().getId())).orElse(null);
        assertNotNull(entity);
        assertEquals(entity.getName(), dto.getName());

        messengerRepository.delete(entity);
    }

    @Test
    public void updateEntity() throws Exception {

        MessengerEntity entity = new MessengerEntity();
        entity.setName("test" + mockedUser.getId());
        entity.setUser(mockedUser);
        entity = messengerRepository.save(entity);

        String newName = entity.getName() + mockedUser.getId();
        MessengerDto dto = new MessengerDto(entity.getId(), newName, mockedUser.getId());

        String j = objectMapper.writeValueAsString(dto);
        var result = this.mockMvc.perform(put("/messenger").with(userToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(j)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<MessengerDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(deserialized.getData().getName(), newName);

        messengerRepository.delete(entity);

    }


    @Test
    @Ignore
    public void deleteEntity() throws Exception {
        MessengerEntity entity = new MessengerEntity();
        entity.setName("Test delete");
        entity = messengerRepository.save(entity);
        entity.setUser(mockedUser);

        this.mockMvc.perform(delete("/messenger/" + entity.getId()).with(userToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNull(messengerRepository.findById(entity.getId()).orElse(null));
    }
}
