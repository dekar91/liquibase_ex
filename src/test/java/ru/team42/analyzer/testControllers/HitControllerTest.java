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
import ru.team42.analyzer.dto.response.HitDto;
import ru.team42.analyzer.entities.ButtonEntity;
import ru.team42.analyzer.entities.ChannelEntity;
import ru.team42.analyzer.entities.HitEntity;
import ru.team42.analyzer.entities.MessengerEntity;
import ru.team42.analyzer.entities.UserEntity;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.repositories.ButtonRepository;
import ru.team42.analyzer.repositories.ChannelRepository;
import ru.team42.analyzer.repositories.HitRepository;
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
public class HitControllerTest extends MockMvcBase {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private MessengerRepository messengerRepository;

    @Autowired
    private ButtonRepository buttonRepository;
    @Autowired
    private HitRepository hitRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity mockedUser;
    private ChannelEntity mockedChannel;
    private MessengerEntity mockedMessenger;
    private ButtonEntity mockedButton;

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

        ButtonEntity button = new ButtonEntity();
        button.setMessenger(this.mockedMessenger);
        button.setChannel(this.mockedChannel);

        mockedButton = buttonRepository.save(button);
    }

    @AfterEach
    void removeMockedUser() {

        buttonRepository.delete(mockedButton);
        channelRepository.delete(mockedChannel);
        messengerRepository.delete(mockedMessenger);
        userRepository.delete(mockedUser);
    }

    @Test
    public void getList() throws Exception {

        this.mockMvc.perform(get("/hit/list").with(userToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void getEntity() throws Exception {
        HitEntity entity = new HitEntity();
        entity.setButton(this.mockedButton);
        HitEntity savedEntity = hitRepository.save(entity);

        var result = this.mockMvc.perform(get("/hit/" + savedEntity.getId()).with(userToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<HitDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertNotNull(deserialized.getData());
        assertThat(deserialized.getData().getId() == savedEntity.getId());
        hitRepository.delete(savedEntity);

    }


    @Test
    public void createEntity() throws Exception {
        HitDto dto = new HitDto(null, this.mockedButton.getId(), "http://test.com", "visit", "{}");

        ObjectMapper o = new ObjectMapper();
        String j = o.writeValueAsString(dto);

        var result = this.mockMvc.perform(post("/hit").with(userToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(j)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<HitDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        HitEntity entity = hitRepository.findById(Objects.requireNonNull(deserialized.getData().getId())).orElse(null);
        assertNotNull(entity);
        assertEquals(entity.getButton().getId(), dto.getButtonId());

        hitRepository.delete(entity);
    }

    @Test
    public void updateEntity() throws Exception {

        HitEntity entity = new HitEntity();
        entity.setButton(mockedButton);
        entity.setUrl("http://test.com");
        entity.setAction("visit");
        entity = hitRepository.save(entity);

        String newName = "pick";
        HitDto dto = new HitDto(entity.getId(), this.mockedButton.getId(), "http://test.com", newName, "{}");

        String j = objectMapper.writeValueAsString(dto);
        var result = this.mockMvc.perform(put("/hit").with(userToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(j)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<HitDto> deserialized = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(deserialized.getData().getAction(), newName);

        hitRepository.delete(entity);

    }


    @Test
    @Ignore
    public void deleteEntity() throws Exception {
        HitEntity entity = new HitEntity();
        entity.setButton(mockedButton);
        entity.setUrl("http://test.com");
        entity.setAction("visit");
        hitRepository.save(entity);


        this.mockMvc.perform(delete("/hit/" + entity.getId()).with(userToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNull(hitRepository.findById(entity.getId()).orElse(null));
    }
}
