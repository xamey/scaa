package com.scaa.scaa.service.agents.instances;

import com.scaa.scaa.model.ComponentServiceRelation;
import com.scaa.scaa.model.Service;
import com.scaa.scaa.service.agents.states.ServiceAgentLifecycle;
import mas.infrastructure.agent.InfraAgent;
import mas.infrastructure.communication.ICommunication;

public class ServiceAgent extends InfraAgent {
    private ComponentServiceRelation serviceRelation;
    private ComponentAgent father;

    public ServiceAgent(ComponentServiceRelation serviceRelation, ICommunication communication) {
        super(new ServiceAgentLifecycle(), communication);
        ((ServiceAgentLifecycle) getLifeCycle()).setAgent(this);
        this.serviceRelation = serviceRelation;
    }

    public ComponentServiceRelation getServiceRelation() {
        return serviceRelation;
    }

    public Service getService() {
        return serviceRelation.getService();
    }

    public ComponentAgent getFather() {
        return father;
    }

    public void setFather(ComponentAgent father) {
        this.father = father;
    }
}
