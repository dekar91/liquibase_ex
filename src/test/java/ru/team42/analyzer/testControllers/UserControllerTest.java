package ru.team42.analyzer.testControllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import ru.team42.analyzer.dto.response.UserDto;
import ru.team42.analyzer.entities.UserEntity;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.repositories.UserRepository;
import ru.team42.analyzer.testsupport.MockMvcBase;

import java.util.Collections;
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
@Disabled
public class UserControllerTest extends MockMvcBase {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getList() throws Exception {

        this.mockMvc.perform(get("/user/list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void getEntity() throws Exception {
        UserEntity entity = new UserEntity();
        entity.setUsername("test");
        entity.setPassword("123456789");

        UserEntity savedEntity = userRepository.save(entity);

        var result = this.mockMvc.perform(get("/user/" + savedEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<UserDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertNotNull(deserialized.getData());
        assertThat(deserialized.getData().getId() == savedEntity.getId());
        userRepository.delete(savedEntity);

    }


    @Test
    @Disabled
    public void createEntity() throws Exception {
        UserDto dto = new UserDto(null, "test create", Collections.emptySet(), 1L);

        ObjectMapper o = new ObjectMapper();
        String j = o.writeValueAsString(dto);

        var result = this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(j)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<UserDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        UserEntity entity = userRepository.findById(Objects.requireNonNull(deserialized.getData().getId())).orElse(null);
        assertNotNull(entity);

        userRepository.delete(entity);
    }

    @Test
    public void updateEntity() throws Exception {

        UserEntity entity = new UserEntity();
        entity.setUsername("testtesttest");
        entity.setPassword("123456789");
        userRepository.save(entity);

        String newName = entity.getUsername() + "123";
        UserDto dto = new UserDto(entity.getId(), "test create", Collections.emptySet(), 1L);


        String j = objectMapper.writeValueAsString(dto);
        var result = this.mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(j)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<UserDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(deserialized.getData().getUsername(), newName);

        userRepository.delete(entity);

    }


    @Test
    @Disabled
    public void deleteEntity() throws Exception {
        UserEntity entity = new UserEntity();
        entity.setUsername("testtesttest");
        entity.setPassword("123456789");

        entity = userRepository.save(entity);

        this.mockMvc.perform(delete("/user/" + entity.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNull(userRepository.findById(entity.getId()).orElse(null));
    }
}
