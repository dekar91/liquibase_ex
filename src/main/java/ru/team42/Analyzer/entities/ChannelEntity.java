package ru.team42.analyzer.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "channel")
public class ChannelEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "channel_generator")
    @SequenceGenerator(name="channel_generator", sequenceName = "channel_id_seq", allocationSize = 1)
    private Long id;

    @Length(min = 2)
    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="id")
    private UserEntity user;

    @OneToMany
    @JoinColumn(name="messenger_id", referencedColumnName="id")
    private List<MessengerEntity> messengers;

    @OneToMany
    @JoinColumn(name="channel_id", referencedColumnName="id")
    private List<ButtonEntity> buttons;

    public void setUserId(Long userId) {
        if(userId != null ) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userId);

            this.setUser(userEntity);
        }
    }
}
