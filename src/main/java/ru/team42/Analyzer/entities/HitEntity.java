package ru.team42.analyzer.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hit")
public class HitEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hit_generator")
    @SequenceGenerator(name = "hit_generator", sequenceName = "hit_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "button_id", referencedColumnName = "id")
    private ButtonEntity button;

    @Column
    private String url;

    @Column
    private String action;

    @Column
    private String data;

}
