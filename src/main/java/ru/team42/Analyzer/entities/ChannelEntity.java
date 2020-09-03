package ru.team42.analyzer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "channel")
public class ChannelEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "channel_generator")
    @SequenceGenerator(name="channel_generator", sequenceName = "channel_id_seq", allocationSize = 1)
    private long id;

    @Column
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="id")
    private UserEntity user;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name="messenger_id", referencedColumnName="id")
    private List<MessengerEntity> messengers;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name="channel_id", referencedColumnName="id")
    private List<ButtonEntity> buttons;

    public void setUserId(long userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        this.setUser(userEntity);
    }
}
