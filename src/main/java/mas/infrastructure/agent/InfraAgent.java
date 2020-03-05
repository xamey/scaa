package mas.infrastructure.agent;

import mas.infrastructure.communication.ICommunication;
import mas.infrastructure.communication.IMessage;
import mas.infrastructure.state.IState;
import mas.infrastructure.state.LifeCycle;

import java.util.ArrayList;
import java.util.Optional;

public class InfraAgent {

    private final InfraAgentReference infraAgentReference;
    private final LifeCycle lifeCycle;
    private final ICommunication myMailBoxManager;

    public InfraAgent(LifeCycle lifeCycle, ICommunication myMailBoxManager) {
        this.infraAgentReference = new InfraAgentReference();
        this.lifeCycle = lifeCycle;
        this.myMailBoxManager = myMailBoxManager;
    }

    public void run() {
        this.lifeCycle.run();
    }


    public InfraAgentReference getInfraAgentReference() {
        return infraAgentReference;
    }

    public IState getState() {
        return lifeCycle.getCurrentState();
    }

    public ArrayList<IMessage> readMessages() {
        return this.myMailBoxManager.receiveMessages(this.infraAgentReference);
    }

    public LifeCycle getLifeCycle() {
        return lifeCycle;
    }

    public Optional<IMessage> readMessage() {
        return this.myMailBoxManager.receiveMessage(this.infraAgentReference);
    }

    @Override
    public String toString() {
        return "INFRA.IDAgent{" + infraAgentReference + '}';
    }
}
