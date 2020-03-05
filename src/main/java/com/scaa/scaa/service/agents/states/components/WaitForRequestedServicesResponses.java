package com.scaa.scaa.service.agents.states.components;

import com.scaa.scaa.model.Service;
import com.scaa.scaa.service.agents.communication.messages.RequestedServiceResponse;
import com.scaa.scaa.service.agents.instances.ComponentAgent;
import com.scaa.scaa.service.agents.states.ComponentAgentLifecycle;
import mas.infrastructure.communication.IMessage;
import mas.infrastructure.state.IState;
import mas.infrastructure.state.LifeCycle;

import java.util.Optional;

public class WaitForRequestedServicesResponses implements IState {
    @Override
    public void execute(LifeCycle c) {
        ComponentAgentLifecycle lifecycle = (ComponentAgentLifecycle) c;
        ComponentAgent thisAgent = lifecycle.getComponentAgent();
        Optional<IMessage> message = thisAgent.readMessage();

        int i = 0;
        while (i < thisAgent.getChildren().size()) {
            if (message.isPresent()) {

                if (message.get() instanceof RequestedServiceResponse) {
                    RequestedServiceResponse messageInstance = (RequestedServiceResponse) message.get();
                    Service requestedService = messageInstance.getRequest().getRequestedService();
                }
            }
        }
    }
}
