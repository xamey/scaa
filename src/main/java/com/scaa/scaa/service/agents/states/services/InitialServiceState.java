package com.scaa.scaa.service.agents.states.services;

import com.scaa.scaa.model.Service;
import com.scaa.scaa.service.agents.AgentAdapter;
import com.scaa.scaa.service.agents.communication.messages.RequestForService;
import com.scaa.scaa.service.agents.communication.messages.RequestedServiceResponse;
import com.scaa.scaa.service.agents.instances.ServiceAgent;
import com.scaa.scaa.service.agents.states.ServiceAgentLifecycle;
import mas.infrastructure.communication.IMessage;
import mas.infrastructure.state.IState;
import mas.infrastructure.state.LifeCycle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class InitialServiceState implements IState {

    private AgentAdapter agentAdapter;


    @Autowired
    public void setAgentAdapter(AgentAdapter agentAdapter) {
        this.agentAdapter = agentAdapter;
    }

    @Override
    public void execute(LifeCycle c) {
        ServiceAgentLifecycle lifeCycle = (ServiceAgentLifecycle) c;
        ServiceAgent agent = lifeCycle.getAgent();
        Optional<IMessage> message = agent.readMessage();
        if (message.isPresent()) {
            // Message de requete pour un service
            if (message.get() instanceof RequestForService) {
                respondToRequestedService(lifeCycle, agent);
            }
        }
    }

    private void respondToRequestedService(ServiceAgentLifecycle lifeCycle, ServiceAgent thisAgent) {
        RequestForService message = (RequestForService) thisAgent.readMessage().get();
        Service matchSubject = message.getRequestedService();
        boolean matched = (thisAgent.getService().equals(matchSubject)
                && (lifeCycle.getAgent().getServiceRelation().isRequired() && message.isGiven())
                || (!lifeCycle.getAgent().getServiceRelation().isRequired() && message.isRequired())
        );
        RequestedServiceResponse response = new RequestedServiceResponse(
                message,
                thisAgent.getService().equals(matchSubject),
                thisAgent.getInfraAgentReference(),
                message.getEmitter());
        AgentAdapter.getInstance().getCommunication().sendMessage(response);
        if (matched) {
            lifeCycle.setCurrentState(new SelectableServiceState());
        }
    }
}
