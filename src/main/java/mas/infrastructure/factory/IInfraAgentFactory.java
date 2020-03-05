package mas.infrastructure.factory;

import mas.infrastructure.agent.InfraAgent;
import mas.infrastructure.communication.ICommunication;
import mas.infrastructure.state.LifeCycle;

public interface IInfraAgentFactory {

    InfraAgent createInfrastructureAgent(LifeCycle lifeCycle, ICommunication myMailBoxManager);
}
