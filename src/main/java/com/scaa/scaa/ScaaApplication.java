package com.scaa.scaa;

import com.scaa.scaa.model.Choice;
import com.scaa.scaa.model.Component;
import com.scaa.scaa.model.ComponentServiceRelation;
import com.scaa.scaa.model.Possibility;
import com.scaa.scaa.service.components.ICSR_platform_adapter_impl;
import com.scaa.scaa.ui.expert.ExpertUIFrame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScaaApplication extends Application {


    @Autowired
    private ICSR_platform_adapter_impl icsr_platform_adapter_impl;

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ScaaApplication.class).headless(false).run(args);
        ExpertUIFrame expertUIFrame = context.getBean(ExpertUIFrame.class);
        expertUIFrame.setVisible(true);

        List<Possibility> possibilities = new ArrayList<>();
        com.scaa.scaa.model.Service s1 = new com.scaa.scaa.model.Service("s1", "service 1");
        com.scaa.scaa.model.Service s2 = new com.scaa.scaa.model.Service("s2", "service 2");
        Component c1 = new Component("c1");
        ComponentServiceRelation csrC1S1required = new ComponentServiceRelation(c1, s1, true);
        ComponentServiceRelation csrC1S2provided = new ComponentServiceRelation(c1, s2, false);

        Component c2 = new Component("c2");
        ComponentServiceRelation csrC2S1provided = new ComponentServiceRelation(c2, s1, false);
        Possibility p1 = new Possibility(csrC1S1required, csrC2S1provided, false);
        Possibility p2 = new Possibility(csrC1S1required, csrC1S2provided, true);
        possibilities.add(p1);
        possibilities.add(p2);
        launch(args);
        Choice choice = expertUIFrame.askForExpertChoice(possibilities);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(new File("src/main/java/com/scaa/scaa/ui/userui/userui.fxml").toURI().toURL());
        primaryStage.setTitle("User UI");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
    }


}
