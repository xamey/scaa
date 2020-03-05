package com.scaa.scaa.service.agents;

import com.scaa.scaa.model.Choice;
import com.scaa.scaa.model.Component;
import com.scaa.scaa.model.ComponentServiceRelation;
import com.scaa.scaa.service.agents.communication.CommunicationImp;
import com.scaa.scaa.service.agents.communication.messages.ComponentSelectedAsFirst;
import com.scaa.scaa.service.agents.instances.ComponentAgent;
import com.scaa.scaa.service.agents.instances.ServiceAgent;
import mas.infrastructure.Infrastructure;
import mas.infrastructure.agent.InfraAgentReference;
import mas.infrastructure.communication.ICommunication;

import java.util.*;

@org.springframework.stereotype.Component
public class AgentAdapter {

    private Infrastructure infrastructure;
    private ICommunication communication;
    private Map<String, ComponentAgent> componentsAgentsMap;
    private Map<String, ServiceAgent> servicesAgentsMap;

    private static AgentAdapter instance = null;

    private AgentAdapter() {
        infrastructure = new Infrastructure();
        communication = new CommunicationImp(infrastructure);
        componentsAgentsMap = new HashMap<>();
        servicesAgentsMap = new HashMap<>();
    }

    public static AgentAdapter getInstance() {
        if (instance == null) {
            instance = new AgentAdapter();
        }
        return instance;
    }

    public static void main(String[] args) {

    }

    public void start() {
        infrastructure.startScheduling();
    }

    public void selectFirstComponent(Component component) {
        InfraAgentReference firstCmp = getComponentAgent(component).getInfraAgentReference();
        communication.sendMessage(new ComponentSelectedAsFirst(firstCmp));
    }

    public ServiceAgent createAgentForService(ComponentServiceRelation service) {
        ServiceAgent serviceAgent = new ServiceAgent(service, communication);
        infrastructure.addAgentToScheduler(serviceAgent);
        this.putServiceAgentToMap(serviceAgent);
        return serviceAgent;
    }

    public ComponentAgent createAgentForComponent(Component component) {
        ComponentAgent componentAgent = new ComponentAgent(component, communication);
        infrastructure.addAgentToScheduler(componentAgent);
        this.putComponentAgentToMap(componentAgent);
        return componentAgent;
    }

    public void resolveConflict(Choice choice) {

    }

    private void putComponentAgentToMap(ComponentAgent agent) {
        componentsAgentsMap.put(agent.getComponent().getName(), agent);
    }

    public List<ComponentAgent> getComponentAgents() {
        return new ArrayList(componentsAgentsMap.values());
    }

    public List<ServiceAgent> getServiceAgents() {
        return new ArrayList(servicesAgentsMap.values());
    }

    public ServiceAgent getServiceAgent(ComponentServiceRelation serviceRelation) {
        return servicesAgentsMap.get(this.constructServiceComponentRelationId(serviceRelation));
    }

    public ComponentAgent getComponentAgent(Component component) {
        return componentsAgentsMap.get(component.getName());
    }

    private String constructServiceComponentRelationId(ComponentServiceRelation serviceRelation) {
        return serviceRelation.getComponent().getName()
                + " -|=|- " + serviceRelation.getService().getId();
    }

    public ICommunication getCommunication() {
        return communication;
    }

    private void putServiceAgentToMap(ServiceAgent agent) {
        String serviceId = constructServiceComponentRelationId(agent.getServiceRelation());
        servicesAgentsMap.put(serviceId, agent);
    }
}
