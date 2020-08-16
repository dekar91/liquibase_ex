package ru.team42.Analyzer.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

//@Entity
//@Table(name = "user")
public class UserEntity {

    @Id
    private long id;

    @Column(columnDefinition = "bigint null")
    private long chat2DeskId;

    @Column
    private LocalDateTime created;

    @OneToMany
    private List<HitEntity> hits;
}
