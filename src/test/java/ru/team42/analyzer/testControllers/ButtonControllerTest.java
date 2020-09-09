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
import ru.team42.analyzer.dto.response.ButtonDto;
import ru.team42.analyzer.entities.ButtonEntity;
import ru.team42.analyzer.entities.ChannelEntity;
import ru.team42.analyzer.entities.MessengerEntity;
import ru.team42.analyzer.entities.UserEntity;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.repositories.ButtonRepository;
import ru.team42.analyzer.repositories.ChannelRepository;
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
public class ButtonControllerTest extends MockMvcBase {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private MessengerRepository messengerRepository;

    @Autowired
    private ButtonRepository buttonRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity mockedUser;
    private ChannelEntity mockedChannel;
    private MessengerEntity mockedMessenger;

    @BeforeEach
    void mockUser() {
        UserEntity entity = new UserEntity();
        entity.setUsername("test123");
        entity.setPassword("test123");

        this.mockedUser = userRepository.save(entity);

        ChannelEntity channelEntity = new ChannelEntity();
        channelEntity.setUser(this.mockedUser);

        MessengerEntity messengerEntity = new MessengerEntity();
        messengerEntity.setUser(this.mockedUser);

        this.mockedChannel = channelRepository.save(channelEntity);
        this.mockedMessenger = messengerRepository.save(messengerEntity);
    }

    @AfterEach
    void removeMockedUser() {

        channelRepository.delete(mockedChannel);
        messengerRepository.delete(mockedMessenger);
        userRepository.delete(mockedUser);
    }

    @Test
    public void getList() throws Exception {

        this.mockMvc.perform(get("/button/list").with(userToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void getEntity() throws Exception {
        ButtonEntity entity = new ButtonEntity();
        ButtonEntity savedEntity = buttonRepository.save(entity);

        var result = this.mockMvc.perform(get("/button/" + savedEntity.getId()).with(userToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<ButtonDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertNotNull(deserialized.getData());
        assertThat(deserialized.getData().getId() == savedEntity.getId());
        buttonRepository.delete(savedEntity);

    }


    @Test
    public void createEntity() throws Exception {
        ButtonDto dto = new ButtonDto(null, "test create" + mockedUser.getId(), mockedChannel.getId(), mockedMessenger.getId());

        ObjectMapper o = new ObjectMapper();
        String j = o.writeValueAsString(dto);

        var result = this.mockMvc.perform(post("/button").with(userToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(j)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<ButtonDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        ButtonEntity entity = buttonRepository.findById(Objects.requireNonNull(deserialized.getData().getId())).orElse(null);
        assertNotNull(entity);
        assertEquals(entity.getName(), dto.getName());

        buttonRepository.delete(entity);
    }

    @Test
    public void updateEntity() throws Exception {

        ButtonEntity entity = new ButtonEntity();
        entity.setName("test" + mockedUser.getId());
        entity.setChannel(mockedChannel);
        entity.setMessenger(mockedMessenger);

        buttonRepository.save(entity);

        String newName = entity.getName() + mockedUser.getId();
        ButtonDto dto = new ButtonDto(entity.getId(), newName, mockedChannel.getId(), mockedMessenger.getId());

        String j = objectMapper.writeValueAsString(dto);
        var result = this.mockMvc.perform(put("/button").with(userToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(j)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<ButtonDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(deserialized.getData().getName(), newName);

        buttonRepository.delete(entity);

    }


    @Test
    @Ignore
    public void deleteEntity() throws Exception {
        ButtonEntity entity = new ButtonEntity();
        entity.setName("Test delete");
        entity.setChannel(mockedChannel);
        entity.setMessenger(mockedMessenger);

        entity = buttonRepository.save(entity);

        this.mockMvc.perform(delete("/button/" + entity.getId()).with(userToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNull(buttonRepository.findById(entity.getId()).orElse(null));
    }
}
