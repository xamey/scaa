package com.scaa.scaa.ui.userui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;

import java.util.List;

public class ComponentChoiceController {

    private final ChangeListener<String> LABEL_CHANGE_LISTENER = new ChangeListener<String>() {
        public void changed(ObservableValue<? extends String> property, String oldValue, String newValue) {
            updateLabelView(newValue);
        }
    };

    private final ChangeListener<Boolean> IS_SELECTED_CHANGE_LISTENER = new ChangeListener<Boolean>() {
        public void changed(ObservableValue<? extends Boolean> property, Boolean oldValue, Boolean newValue) {
            updateIsSelectedView(newValue);
        }
    };

    @FXML
    private Label labelView;

    @FXML
    private RadioButton isSelectedView;

    @FXML
    private TextArea tfRequiredInterfaces;

    @FXML
    private TextArea tfProvidedInterfaces;

    private ComponentChoiceModel model;

    public ComponentChoiceModel getModel() {
        return model;
    }

    public void setModel(ComponentChoiceModel model) {
        if (this.model != null)
            removeModelListeners();
        this.model = model;
        setupModelListeners();
        updateView();
    }

    private void removeModelListeners() {
        model.labelProperty().removeListener(LABEL_CHANGE_LISTENER);
        model.isSelectedProperty().removeListener(IS_SELECTED_CHANGE_LISTENER);
        isSelectedView.selectedProperty().unbindBidirectional(model.isSelectedProperty());
    }

    private void setupModelListeners() {
        model.labelProperty().addListener(LABEL_CHANGE_LISTENER);
        model.isSelectedProperty().addListener(IS_SELECTED_CHANGE_LISTENER);
        isSelectedView.selectedProperty().bindBidirectional(model.isSelectedProperty());
    }

    private void updateView() {
        updateLabelView();
        updateIsSelectedView();
        updateRequiredInterfacesView();
        updateProvidedInterfacesView();
    }

    private void updateRequiredInterfacesView() {
        updateRequiredInterfacesView(model.getRequiredInterfaces());
    }

    private void updateRequiredInterfacesView(List<String> requiredInterfaces) {
        tfRequiredInterfaces.setText(String.join("\n", requiredInterfaces));
    }

    private void updateProvidedInterfacesView() {
        updateProvidedInterfacesView(model.getProvidedInterfaces());
    }

    private void updateProvidedInterfacesView(List<String> providedInterfaces) {
        tfProvidedInterfaces.setText(String.join("\n", providedInterfaces));
    }

    private void updateLabelView() {
        updateLabelView(model.getLabel());
    }

    private void updateLabelView(String newValue) {
        labelView.setText(newValue);
    }

    private void updateIsSelectedView() {
        updateIsSelectedView(model.isSelected());
    }

    private void updateIsSelectedView(boolean newValue) {
        isSelectedView.setSelected(newValue);
    }

}
