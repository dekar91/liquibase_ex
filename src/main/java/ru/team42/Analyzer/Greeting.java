package ru.team42.Analyzer;

import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Entity
@Table(name = "greet")
public class Greeting {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "greeting_generator")
    @SequenceGenerator(name="greeting_generator", sequenceName = "greet_id_seq", allocationSize = 1)
    private long id;

    @Column(columnDefinition = "text null")
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}