package ru.team42.Analyzer.entities;

import javax.persistence.*;

@Entity
@Table(name = "channels")
public class ChannelEntity {

    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="id")
    private UserEntity user;
}
