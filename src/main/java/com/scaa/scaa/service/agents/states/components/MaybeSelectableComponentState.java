package com.scaa.scaa.service.agents.states.components;

import com.scaa.scaa.service.agents.AgentAdapter;
import com.scaa.scaa.service.agents.communication.messages.AskToServiceForConnection;
import com.scaa.scaa.service.agents.communication.messages.ResponseFromAgentChildOk;
import com.scaa.scaa.service.agents.instances.ComponentAgent;
import com.scaa.scaa.service.agents.instances.ServiceAgent;
import com.scaa.scaa.service.agents.states.ComponentAgentLifecycle;
import mas.infrastructure.state.IState;
import mas.infrastructure.state.LifeCycle;
import org.springframework.beans.factory.annotation.Autowired;

public class MaybeSelectableComponentState implements IState {
    /**
     * This function allows to run the action associated to the current state
     *
     * @param c the life cycle of the agent
     */

    boolean sent = false;
    private AgentAdapter agentAdapter;
    int nbSent = 0;

    @Autowired
    public void setAgentAdapter(AgentAdapter agentAdapter) {
        this.agentAdapter = agentAdapter;
    }

    @Override
    public void execute(LifeCycle c) {
        ComponentAgentLifecycle lifeCycle = (ComponentAgentLifecycle) c;
        ComponentAgent agent = lifeCycle.getComponentAgent();
        if (!sent) {
            for (ServiceAgent serviceAgent : agent.getChildren()) {
                if (serviceAgent.getServiceRelation().isRequired()) {
                    AskToServiceForConnection askToServiceForConnection = new AskToServiceForConnection(agent.getInfraAgentReference(), serviceAgent.getInfraAgentReference());
                    agentAdapter.getCommunication().sendMessage(askToServiceForConnection);
                    nbSent++;
                }
            }
            sent = true;
        }
        else {
            if (nbSent > 0) {
                if (agent.readMessage().isPresent()) {
                    if (agent.readMessage().get() instanceof ResponseFromAgentChildOk) {
                        nbSent--;
                    }
                }
            }
            else {
                c.setCurrentState(new SelectableComponentState());
            }
        }
    }
}
