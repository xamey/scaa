package com.scaa.scaa.ui.userui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import userui.models.ComponentWithInterfaces;

import java.util.List;

public class ComponentChoiceModel {

    private final StringProperty label;

    private final BooleanProperty isSelected;

    private ComponentWithInterfaces component;

    public ComponentChoiceModel(StringProperty label, BooleanProperty isSelected) {
        this(null);
    }

    public ComponentChoiceModel(ComponentWithInterfaces component) {
        this.component = component;
        this.label = new SimpleStringProperty(component != null ? component.getName(): null);
        this.isSelected = new SimpleBooleanProperty(false);
    }

    public String getLabel() {
        return label.get();
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public StringProperty labelProperty() {
        return label;
    }

    public boolean isSelected() {
        return isSelected.get();
    }

    public void setSelected(boolean isSelected) {
        this.isSelected.set(isSelected);
    }

    public BooleanProperty isSelectedProperty() {
        return isSelected;
    }

    public List<String> getRequiredInterfaces() {
        return component.getRequiredInterfaces();
    }

    public List<String> getProvidedInterfaces() {
        return component.getProvidedInterfaces();
    }

}