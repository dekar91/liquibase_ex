package ru.team42.Analyzer.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hits")
public class HitEntity {

    @Id
    private long id;

    @Column(name = "channel_id")
    private long channelId;

    @ManyToOne
    private ChannelEntity channel;

    @Column
    private LocalDateTime created;

}
