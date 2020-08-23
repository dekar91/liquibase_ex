package ru.team42.analyzer.entities;

import javax.persistence.*;

@Entity
@Table(name = "messenger")
public class MessengerEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messenger_generator")
    @SequenceGenerator(name="messenger_generator", sequenceName = "messenger_id_seq")
    private long id;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name="channel_id", referencedColumnName="id")
    private ChannelEntity channel;
}
