package ru.team42.Analyzer.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hits")
public class HitEntity {

    @Id
    private long id;

//    @Column(columnDefinition = "channel_id")
//    private long channelId;


    @ManyToOne
    @JoinColumn(name="channel_id", referencedColumnName="id")
    private ChannelEntity channel;

    @Column
    private LocalDateTime created;

}
