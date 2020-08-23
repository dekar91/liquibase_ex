package ru.team42.analyzer.entities;

import javax.persistence.*;

@Entity
@Table(name = "hit")
public class HitEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hit_generator")
    @SequenceGenerator(name="hit_generator", sequenceName = "hit_id_seq")
    private long id;

    @ManyToOne
    @JoinColumn(name="channel_id", referencedColumnName="id")
    private ChannelEntity channel;

    @ManyToOne
    @JoinColumn(name="messenger_id", referencedColumnName="id")
    private MessengerEntity messenger;

    @Column
    private String data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ChannelEntity getChannel() {
        return channel;
    }

    public void setChannel(ChannelEntity channel) {
        this.channel = channel;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public MessengerEntity getMessenger() {
        return messenger;
    }

    public void setMessenger(MessengerEntity messenger) {
        this.messenger = messenger;
    }
}
