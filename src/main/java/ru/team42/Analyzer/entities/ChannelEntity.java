package ru.team42.Analyzer.entities;

import javax.persistence.*;

@Entity
@Table(name = "channels")
public class ChannelEntity {

    @Id
    private long id;

    @Column(name = "user_id")
    private long userId;

    @ManyToOne
    private UserEntity user;
}
