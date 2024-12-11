package net.ticket.eventticketingsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "configuration")
public class Configuration {
    @Id
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "PARAMETER", nullable = false)
    private String parameter;

    @Column(name = "DEFAULT_VALUE", nullable = false)
    private Long defaultValue;

    @Column(name = "VALUE", nullable = false)
    private Long value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Long getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Long defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
