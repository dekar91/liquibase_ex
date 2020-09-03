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
import ru.team42.analyzer.dto.response.ChannelDto;
import ru.team42.analyzer.entities.ChannelEntity;
import ru.team42.analyzer.entities.UserEntity;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.repositories.ChannelRepository;
import ru.team42.analyzer.repositories.UserRepository;
import ru.team42.analyzer.testsupport.MockMvcBase;

import java.util.Collections;

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
public class ChannelControllerTest extends MockMvcBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;

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
        userRepository.delete(mockedUser);
    }

    @Test
    public void getList() throws Exception {

        this.mockMvc.perform(get("/channel/list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void getEntity() throws Exception {
        ChannelEntity entity = new ChannelEntity();
        entity.setUser(mockedUser);
        ChannelEntity savedEntity = channelRepository.save(entity);

        var result = this.mockMvc.perform(get("/channel/" + savedEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<ChannelDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertNotNull(deserialized.getData());
        assertThat(deserialized.getData().getId() == savedEntity.getId());
        channelRepository.delete(savedEntity);

    }


    @Test
    public void createEntity() throws Exception {
        ChannelDto dto = new ChannelDto(null, "test create" + mockedUser.getId(), mockedUser.getId(), Collections.emptyList(), Collections.emptyList());

        ObjectMapper o = new ObjectMapper();
        String j = o.writeValueAsString(dto);

        var result = this.mockMvc.perform(post("/channel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(j)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<ChannelDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        ChannelEntity entity = channelRepository.findById(deserialized.getData().getId()).orElse(null);
        assertNotNull(entity);
        assertEquals(entity.getName(), dto.getName());

        channelRepository.delete(entity);

    }

    @Test
    public void updateEntity() throws Exception {

        ChannelEntity entity = new ChannelEntity();
        entity.setName("test" + mockedUser.getId());
        entity.setUser(mockedUser);
        entity = channelRepository.save(entity);

        String newName = entity.getName() + mockedUser.getId();
        ChannelDto dto =
                new ChannelDto(entity.getId(), newName, mockedUser.getId(), Collections.emptyList(), Collections.emptyList());


        String j = objectMapper.writeValueAsString(dto);
        var result = this.mockMvc.perform(put("/channel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(j)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<ChannelDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(deserialized.getData().getName(), newName);

        channelRepository.delete(entity);

    }


    @Test
    @Ignore
    public void deleteEntity() throws Exception {
        ChannelEntity entity = new ChannelEntity();
        entity.setName("Test delete");
        entity.setUser(mockedUser);

        entity = channelRepository.save(entity);

        this.mockMvc.perform(delete("/channel/" + entity.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNull(channelRepository.findById(entity.getId()).orElse(null));

    }
}
