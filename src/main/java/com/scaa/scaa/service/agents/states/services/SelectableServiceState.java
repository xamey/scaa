package com.scaa.scaa.service.agents.states.services;

import com.scaa.scaa.service.agents.communication.messages.ServiceIsSelectedMessage;
import com.scaa.scaa.service.agents.communication.messages.ServiceIsUnselectedMessage;
import com.scaa.scaa.service.agents.instances.ServiceAgent;
import com.scaa.scaa.service.agents.states.ServiceAgentLifecycle;
import mas.infrastructure.state.IState;
import mas.infrastructure.state.LifeCycle;

public class SelectableServiceState implements IState {
    @Override
    public void execute(LifeCycle c) {
        ServiceAgentLifecycle lifeCycle = (ServiceAgentLifecycle) c;
        ServiceAgent agent = lifeCycle.getAgent();
        if (agent.readMessage().isPresent()) {
            if (agent.readMessage().get() instanceof ServiceIsSelectedMessage) {
                lifeCycle.setCurrentState(new SelectedServiceState());
            } else if (agent.readMessage().get() instanceof ServiceIsUnselectedMessage) {
                lifeCycle.setCurrentState(new InitialServiceState());
            }
        }
    }
}
