package com.scaa.scaa.service.agents.states.components;

import com.scaa.scaa.model.ComponentServiceRelation;
import com.scaa.scaa.model.Service;
import com.scaa.scaa.service.agents.AgentAdapter;
import com.scaa.scaa.service.agents.communication.messages.AskForComponentServiceConnection;
import com.scaa.scaa.service.agents.communication.messages.ComponentSelectedAsFirst;
import com.scaa.scaa.service.agents.communication.messages.RequestForService;
import com.scaa.scaa.service.agents.instances.ComponentAgent;
import com.scaa.scaa.service.agents.instances.ServiceAgent;
import com.scaa.scaa.service.agents.instances.ServiceAgent;
import com.scaa.scaa.service.agents.states.ComponentAgentLifecycle;
import mas.infrastructure.agent.InfraAgent;
import mas.infrastructure.agent.InfraAgentReference;
import mas.infrastructure.communication.IMessage;
import mas.infrastructure.state.IState;
import mas.infrastructure.state.LifeCycle;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InitialComponentState implements IState {
    @Override
    public void execute(LifeCycle c) {
        ComponentAgentLifecycle lifecycle = (ComponentAgentLifecycle) c;
        ComponentAgent thisAgent = lifecycle.getComponentAgent();
        Optional<IMessage> message = thisAgent.readMessage();
        if (message.isPresent()) {

            // Reécupere les services et envoie ds messages
            if (message.get() instanceof ComponentSelectedAsFirst) {
                List<ComponentServiceRelation> childrenServices = thisAgent.getChildren().stream()
                        .map(ServiceAgent::getServiceRelation).collect(Collectors.toList());
                List<InfraAgentReference> targets = AgentAdapter.getInstance().getComponentAgents()
                        .stream().filter(componentAgent -> !componentAgent.getComponent().equals(thisAgent.getComponent()))
                        .map(InfraAgent::getInfraAgentReference)
                        .collect(Collectors.toList());
                for (ComponentServiceRelation serviceRelation : childrenServices) {
                    RequestForService request = new RequestForService(
                            thisAgent.getInfraAgentReference(), targets, serviceRelation.getService(), serviceRelation.isRequired());
                    AgentAdapter.getInstance().getCommunication().sendMessage(request);
                }
                lifecycle.setCurrentState(new WaitForRequestedServicesResponses());
            }

            // Message de requete pour un service
            else if (message.get() instanceof AskForComponentServiceConnection) {
                AskForComponentServiceConnection request = (AskForComponentServiceConnection) message.get();
                Service service = request.getService();
                boolean required = request.isRequired();
                for (ServiceAgent serviceAgent : thisAgent.getChildren()) {
                    if (serviceAgent.getService().equals(service) && serviceAgent.getServiceRelation().isRequired() == required) {
                        c.setCurrentState(new MaybeSelectableComponentState());
                    }
                }
            }
        }
    }
}
