package com.scaa.scaa.ui.userui;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class ComponentChoiceCell extends ListCell<ComponentChoiceModel> {

    @Override
    protected void updateItem(ComponentChoiceModel model, boolean bln) {
        super.updateItem(model, bln);
        if (model != null) {
            // URL location = ComponentChoiceController.class.getResource("component_choice_view.fxml");
            URL location = null;
            try {
                location = new File("src/main/java/com/scaa/scaa/ui/userui/component_choice_view.fxml").toURI().toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e.getMessage());
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            try {
                Node root = (Node) fxmlLoader.load(location.openStream());
                ComponentChoiceController controller = (ComponentChoiceController) fxmlLoader.getController();
                controller.setModel(model);
                setGraphic(root);
            } catch (IOException ioe) {
                throw new IllegalStateException(ioe);
            }
        }
    }
}