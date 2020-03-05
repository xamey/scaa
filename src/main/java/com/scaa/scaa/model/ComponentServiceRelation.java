package com.scaa.scaa.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class ComponentServiceRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    @ManyToOne
    Component component;
    @NotNull
    @ManyToOne
    Service service;
    @NotNull
    boolean required;

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public ComponentServiceRelation(Component component, Service service, boolean required) {
        this.component = component;
        this.service = service;
        this.required = required;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
