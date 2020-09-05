package ru.team42.analyzer.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "integration")
public class IntegrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "integration_generator")
    @SequenceGenerator(name="integration_generator", sequenceName = "integration_id_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private IntegrationType type;

    @Column
    private String name;

    @Column
    private String settings;

}
