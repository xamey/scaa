package mas.infrastructure.scheduler;

import mas.infrastructure.agent.InfraAgentReference;
import mas.infrastructure.state.IState;


public interface SchedulerListener {

    void onStateChange(InfraAgentReference infraAgentReference, IState abstractState);
}
