package com.scaa.scaa.service.orchestrator;

import com.scaa.scaa.model.Choice;
import com.scaa.scaa.model.ComponentServiceRelation;
import com.scaa.scaa.model.Possibility;
import com.scaa.scaa.service.agents.AgentAdapter;
import com.scaa.scaa.service.agents.instances.ComponentAgent;
import com.scaa.scaa.service.agents.instances.ServiceAgent;
import com.scaa.scaa.service.agents.states.ComponentAgentLifecycle;
import com.scaa.scaa.service.agents.states.ServiceAgentLifecycle;
import com.scaa.scaa.service.agents.states.components.SelectedAsFirstComponentState;
import com.scaa.scaa.service.components.ICSR_Broker_impl;
import com.scaa.scaa.service.conflicts.ConflictResolver_impl;
import com.scaa.scaa.ui.expert.ExpertUIFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Orchestrator_impl implements IOrchestrator {

    private ConflictResolver_impl conflictResolver_impl;
    private ExpertUIFrame expertUIFrame;
    private AgentAdapter agentAdapter;
    private ICSR_Broker_impl icsr_broker_impl;
    private List<ComponentServiceRelation> list;

    public Orchestrator_impl() {
        //initAgents();
    }

    @Autowired
    public void setIcsr_broker_impl(ICSR_Broker_impl icsr_broker_impl) {
        this.icsr_broker_impl = icsr_broker_impl;
    }


    @Autowired
    public void setConflictResolver_impl(ConflictResolver_impl conflictResolver_impl) {
        this.conflictResolver_impl = conflictResolver_impl;
    }


    @Autowired
    public void setExpertUIFrame(ExpertUIFrame expertUIFrame) {
        this.expertUIFrame = expertUIFrame;
    }

    @Autowired
    public void setAgentAdapter(AgentAdapter agentAdapter) {
        this.agentAdapter = agentAdapter;
    }

    @Override
    public ComponentServiceRelation resolveConflict(ComponentServiceRelation asker, List<ComponentServiceRelation> candidates) throws InterruptedException {
        return conflictResolver_impl.resolveConflict(asker, candidates);
    }

    @Override
    public Choice askForExpertChoice(List<Possibility> possibilities) throws InterruptedException {
        return expertUIFrame.askForExpertChoice(possibilities);
    }

    public void initAgents() {
        List<ComponentServiceRelation> componentServiceRelations = icsr_broker_impl.getComponentServiceRelationList();
        for (ComponentServiceRelation c : componentServiceRelations) {
            if (agentAdapter.getComponentAgent(c.getComponent()) != null) {
                agentAdapter.createAgentForComponent(c.getComponent());
            }
            ServiceAgent serviceAgent = agentAdapter.createAgentForService(c);
            ServiceAgentLifecycle serviceAgentLifecycle = (ServiceAgentLifecycle) serviceAgent.getLifeCycle();
            serviceAgentLifecycle.setAgent(serviceAgent);
            serviceAgent.setFather(agentAdapter.getComponentAgent(c.getComponent()));
            ComponentAgent componentAgent = agentAdapter.getComponentAgent(c.getComponent());
            componentAgent.addChild(serviceAgent);
            ComponentAgentLifecycle componentAgentLifecycle = (ComponentAgentLifecycle) componentAgent.getLifeCycle();
            componentAgentLifecycle.setComponentAgent(componentAgent);
        }
    }

    public void constructApplication(ComponentServiceRelation componentServiceRelation) {
        ComponentAgent componentAgent = agentAdapter.getComponentAgent(componentServiceRelation.getComponent());
        componentAgent.getLifeCycle().setCurrentState(new SelectedAsFirstComponentState());
    }

    public void showApplicationConstructed() {

    }
}
