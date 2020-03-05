package com.scaa.scaa.ui.userui;

import com.scaa.scaa.model.ComponentServiceRelation;
import com.scaa.scaa.service.components.ICSR_Broker;
import com.scaa.scaa.service.components.ICSR_Broker_impl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.springframework.stereotype.Component;
import userui.models.ComponentWithInterfaces;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Controller implements Initializable {

    public ICSR_Broker icsr_broker = new ICSR_Broker_impl();

    @FXML
    private VBox root;

    @FXML
    private ListView<ComponentChoiceModel> componentContainer;

    @FXML
    private Button btnSubmit;

    private List<ComponentChoiceModel> uiComponents;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        componentContainer.prefWidthProperty().bind(root.prefWidthProperty());
        componentContainer.setCellFactory(new Callback<ListView<ComponentChoiceModel>, ListCell<ComponentChoiceModel>>() {
            public ListCell<ComponentChoiceModel> call(ListView<ComponentChoiceModel> p) {
                return new ComponentChoiceCell();
            }
        });
        btnSubmit.onMouseClickedProperty().addListener((observableValue, eventHandler, t1) -> {
            if (!btnSubmit.isDisabled()) {
                // todo lancer la composition
            }
        });
        setComponents(getComponentsWithInterfaces());
    }

    @FXML
    private void handleForceChange(ActionEvent event) {
        if (componentContainer != null && componentContainer.getItems().size() > 0) {
            boolean isSelected = componentContainer.getItems().get(0).isSelected();
            componentContainer.getItems().get(0).setSelected(!isSelected);
        }
    }

    private void setComponents(List<ComponentWithInterfaces> components) {
        uiComponents = components.stream().map(componentWithInterfaces -> {
            ComponentChoiceModel uiComponent = new ComponentChoiceModel(componentWithInterfaces);
            uiComponent.isSelectedProperty().addListener((o, oldValue, newValue) -> {
                if (newValue) {
                    uncheckAllChoiceBut(uiComponent);
                }
            });
            return uiComponent;
        }).collect(Collectors.toList());
        componentContainer.setItems(FXCollections.observableArrayList(uiComponents));
    }

    private void uncheckAllChoiceBut(ComponentChoiceModel except) {
        for (ComponentChoiceModel choiceModel : uiComponents) {
            if (choiceModel != except) {
                choiceModel.setSelected(false);
            }
        }
    }

    private List<ComponentWithInterfaces> getComponentsWithInterfaces() {
        List<ComponentServiceRelation> list = icsr_broker.getComponentServiceRelationList();
        List<ComponentWithInterfaces> components = new LinkedList<>();
        for (ComponentServiceRelation relation : list) {
            Optional<ComponentWithInterfaces> currentComponent = getComponent(components, relation.getComponent().getName());
            if (!currentComponent.isPresent()) {
                ComponentWithInterfaces component = new ComponentWithInterfaces(relation.getComponent().getName());
                if (relation.isRequired()) {
                    component.addRequiredInterface(relation.getService().getName());
                } else {
                    component.addProvidedInterface(relation.getService().getName());
                }
                components.add(component);
            } else {
                if (relation.isRequired()) {
                    currentComponent.get().addRequiredInterface(relation.getService().getName());
                } else {
                    currentComponent.get().addProvidedInterface(relation.getService().getName());
                }
            }
        }
        return components;
    }

    private Optional<ComponentWithInterfaces> getComponent(List<ComponentWithInterfaces> components, String name) {
        return components.stream().filter(c -> c.getName().equals(name)).findFirst();
    }

}
