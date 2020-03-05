package mas.infrastructure.factory;

import mas.infrastructure.agent.InfraAgent;
import mas.infrastructure.communication.ICommunication;
import mas.infrastructure.directory.IAgentDirectory;
import mas.infrastructure.scheduler.IScheduler;
import mas.infrastructure.state.LifeCycle;

public class InfraAgentFactory implements IInfraAgentFactory, ISuicideService {
    private final IAgentDirectory annuaire;
    private final IScheduler scheduler;

    public InfraAgentFactory(IAgentDirectory directory, IScheduler scheduler) {
        this.annuaire = directory;
        this.scheduler = scheduler;
    }

    @Override
    public InfraAgent createInfrastructureAgent(LifeCycle lifeCycle, ICommunication myMailBoxManager) {
        InfraAgent infraAgent = new InfraAgent(lifeCycle, myMailBoxManager);
        annuaire.addAgent(infraAgent);
        scheduler.addAgentToScheduler(infraAgent);
        return infraAgent;
    }

    @Override
    public void suicide(InfraAgent infraAgent) {
        annuaire.removeAgent(infraAgent.getInfraAgentReference());
        scheduler.deleteAgentFromScheduler(infraAgent);
    }

}
