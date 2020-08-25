package ru.team42.analyzer.entities;

import javax.persistence.*;

@Entity
@Table(name = "channel")
public class ChannelEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "channel_generator")
    @SequenceGenerator(name="channel_generator", sequenceName = "channel_id_seq")
    private long id;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="id")
    private UserEntity user;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
