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
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name="channel_id", referencedColumnName="id")
    private ChannelEntity channel;

    @ManyToOne
    @JoinColumn(name="messenger_id", referencedColumnName="id")
    private MessengerEntity messenger;

    public void setChannelId(Long id) {
        if(id != null) {
            ChannelEntity entity = new ChannelEntity();
            entity.setId(id);
            this.setChannel(channel);
        }
    }

    public Long getChannelId() {
        return this.getChannel() != null ? this.getChannel().getId() : null;
    }

    public Long getMessengerId() {
        return this.getMessenger() != null ? this.getMessenger().getId() : null;
    }

    public void setMessengerId(Long id) {
        if(id != null) {
            MessengerEntity entity = new MessengerEntity();
            entity.setId(id);
            this.setMessenger(entity);
        }
    }
}
