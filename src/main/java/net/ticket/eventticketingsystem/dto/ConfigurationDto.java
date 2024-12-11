package net.ticket.eventticketingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ConfigurationDto {
    private int id;
    private String parameter;
    private Long defaultValue;
    private Long value;

    public ConfigurationDto(int id, String parameter, Long defaultValue, Long value) {
        this.id = id;
        this.parameter = parameter;
        this.defaultValue = defaultValue;
        this.value = value;
    }

    public ConfigurationDto() {
    }

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
