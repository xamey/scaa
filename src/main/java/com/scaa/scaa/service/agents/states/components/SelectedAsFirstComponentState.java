package com.scaa.scaa.service.agents.states.components;

import com.scaa.scaa.service.agents.AgentAdapter;
import com.scaa.scaa.service.agents.communication.messages.AskForComponentServiceConnection;
import com.scaa.scaa.service.agents.communication.messages.ResponseForComponentServiceConnection;
import com.scaa.scaa.service.agents.instances.ComponentAgent;
import com.scaa.scaa.service.agents.instances.ServiceAgent;
import com.scaa.scaa.service.agents.states.ComponentAgentLifecycle;
import com.scaa.scaa.service.orchestrator.Orchestrator_impl;
import mas.infrastructure.agent.InfraAgent;
import mas.infrastructure.agent.InfraAgentReference;
import mas.infrastructure.state.IState;
import mas.infrastructure.state.LifeCycle;
import org.springframework.beans.factory.annotation.Autowired;

public class SelectedAsFirstComponentState implements IState {

    Orchestrator_impl orchestrator_impl;
    AgentAdapter agentAdapter;
    boolean sent = false;
    int msg;

    @Autowired
    public void setOrchestrator_impl(Orchestrator_impl orchestrator_impl) {
        this.orchestrator_impl = orchestrator_impl;
    }

    @Autowired
    public void setAgentAdapter(AgentAdapter agentAdapter) {
        this.agentAdapter = agentAdapter;
    }

    @Override
    public void execute(LifeCycle c) {
        ComponentAgentLifecycle lifeCycle = (ComponentAgentLifecycle) c;
        ComponentAgent agent = lifeCycle.getComponentAgent();
        if (!sent) {
            msg = agent.getChildren().size();
            for (ServiceAgent serviceAgent : agent.getChildren()) {
                AskForComponentServiceConnection askForComponentServiceConnection = new AskForComponentServiceConnection(agent.getInfraAgentReference(), serviceAgent.getService(), serviceAgent.getServiceRelation().isRequired());
                agentAdapter.getCommunication().sendMessageBroadcast(askForComponentServiceConnection);
            }
        }
        else {
            if (msg > 0) {
                if (agent.readMessage().isPresent()) {
                    if (agent.readMessage().get() instanceof ResponseForComponentServiceConnection) {
                        msg--;
                    }
                }
            }
            else {
                orchestrator_impl.showApplicationConstructed();
                c.setCurrentState(new InitialComponentState());
            }

        }

    }
}
