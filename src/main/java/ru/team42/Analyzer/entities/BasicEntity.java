package ru.team42.analyzer.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

@MappedSuperclass
public class BasicEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Calendar created = Calendar.getInstance();

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }
}
