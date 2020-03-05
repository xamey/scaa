package com.scaa.scaa.service.agents.instances;

import com.scaa.scaa.model.Component;
import com.scaa.scaa.service.agents.states.ComponentAgentLifecycle;
import mas.infrastructure.agent.InfraAgent;
import mas.infrastructure.communication.ICommunication;

import java.util.ArrayList;
import java.util.List;

public class ComponentAgent extends InfraAgent {
    private Component component;
    private List<ServiceAgent> children;

    public ComponentAgent(Component component, ICommunication communication) {
        super(new ComponentAgentLifecycle(), communication);
        this.component = component;
        this.children = new ArrayList<>();
    }

    public Component getComponent() {
        return component;
    }

    public List<ServiceAgent> getChildren() {
        return children;
    }

    public void addChild(ServiceAgent child) {
        this.children.add(child);
    }
}
