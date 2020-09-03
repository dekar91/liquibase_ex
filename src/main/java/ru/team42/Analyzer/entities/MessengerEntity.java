package ru.team42.analyzer.entities;

import lombok.Getter;
import lombok.Setter;
import ru.team42.analyzer.dto.MessengerType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "messenger")
public class MessengerEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messenger_generator")
    @SequenceGenerator(name="messenger_generator", sequenceName = "messenger_id_seq", allocationSize = 1)
    private long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column
    private MessengerType type;


    public Long getUserId() {
        return user != null ? user.getId() : null;
    }
}
