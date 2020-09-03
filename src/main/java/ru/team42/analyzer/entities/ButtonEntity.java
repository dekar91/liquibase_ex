package ru.team42.analyzer.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "button")
public class ButtonEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "button_generator")
    @SequenceGenerator(name="button_generator", sequenceName = "button_id_seq", allocationSize = 1)
    private long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name="channel_id", referencedColumnName="id")
    private ChannelEntity channel;

    @ManyToOne
    @JoinColumn(name="messenger_id", referencedColumnName="id")
    private MessengerEntity messenger;
}
