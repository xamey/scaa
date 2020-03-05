package com.scaa.scaa.service.components.dto;

import java.util.List;

public class ComponentDto {
    private String name;
    private ServiceDto[] services;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceDto[] getServices() {
        return services;
    }

    public void setServices(ServiceDto[] services) {
        this.services = services;
    }

    public ComponentDto(String name, ServiceDto[] services) {
        this.name = name;
        this.services = services;
    }
}
