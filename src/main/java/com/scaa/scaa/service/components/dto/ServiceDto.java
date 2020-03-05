package com.scaa.scaa.service.components.dto;

public class ServiceDto {
    private String name;
    private String description;
    private boolean required;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public ServiceDto(String name, String description, boolean required) {
        this.name = name;
        this.description = description;
        this.required = required;
    }
}
