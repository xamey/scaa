package com.scaa.scaa.service.components.dto;

public class FileDto {
    private ComponentDto[] components;
    private ApplicationDto[] applications;

    public ComponentDto[] getComponents() {
        return components;
    }

    public void setComponents(ComponentDto[] components) {
        this.components = components;
    }

    public ApplicationDto[] getApplications() {
        return applications;
    }

    public void setApplications(ApplicationDto[] applications) {
        this.applications = applications;
    }

    public FileDto(ComponentDto[] components, ApplicationDto[] applications) {
        this.components = components;
        this.applications = applications;
    }
}
