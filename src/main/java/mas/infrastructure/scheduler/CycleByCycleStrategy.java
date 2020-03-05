package mas.infrastructure.scheduler;

import mas.infrastructure.agent.InfraAgent;

import java.util.List;

public class CycleByCycleStrategy implements ISchedulingStrategies {

    private final List<InfraAgent> listAgentsToSchedule; // list of observed agents
    private final List<SchedulerListener> schedulerListeners; // list of observers
    private int currentAgentCycle;
    private int maxCycleAgent;

    public CycleByCycleStrategy(List<InfraAgent> listInfraAgent, List<SchedulerListener> currentSchedulerListeners) {
        listAgentsToSchedule = listInfraAgent;
        this.schedulerListeners = currentSchedulerListeners;
        this.currentAgentCycle = 0;
        this.maxCycleAgent = 100;
        changeSpeed(EnumSpeed.HUNDRED);
    }


    @Override
    public void startScheduling() {
        //Initialize the parameters for the execution
        boolean isRunning = true;
        this.currentAgentCycle = 0;

        InfraAgent currentInfraAgent;
        while (isRunning) {
            synchronized (this) {

                while (this.currentAgentCycle < this.maxCycleAgent && listAgentsToSchedule.size() > 0) {
                    currentInfraAgent = listAgentsToSchedule.get(0);
                    currentInfraAgent.run(); //Launch the behavior of the agent in its PERCEPTION State
                    currentInfraAgent.run(); //Launch the behavior of the agent in its DECISION State
                    currentInfraAgent.run(); //Launch the behavior of the agent in its ACTION State
                    listAgentsToSchedule.remove(currentInfraAgent);
                    listAgentsToSchedule.add(currentInfraAgent);
                    this.currentAgentCycle++;
                }
            }
        }
    }

    /**
     * Start a special scheduling cycle with a set of agents and for a certain number of cycles.
     * An example of a use case for this method, is to treat the feedback : scheduling the agents which are supposed to treat the feedback.
     *
     * @param listAgentsToSchedule :   the list of agents to schedule
     * @param numberCycles         :   the number of agent cycle to run (One cycle = Perception, Decision, Action)
     */
    @Override
    public void startSpecialScheduling(List<InfraAgent> listAgentsToSchedule, int numberCycles) {
        int currentCycle = 0;
        //OCELogger.log(Level.INFO, "----------------------------------------------------------------------------------------- STARTING SPECIAL SCHEDULING -------------------------------------------------------------------------------------------- \n");
        InfraAgent currentInfraAgent;

        while (currentCycle < numberCycles && listAgentsToSchedule.size() > 0) {
            currentInfraAgent = listAgentsToSchedule.get(0);
            currentInfraAgent.run(); //Launch the behavior of the agent in its PERCEPTION State
            currentInfraAgent.run(); //Launch the behavior of the agent in its DECISION State
            currentInfraAgent.run(); //Launch the behavior of the agent in its ACTION State
            listAgentsToSchedule.remove(currentInfraAgent);
            listAgentsToSchedule.add(currentInfraAgent);
            currentCycle++;
        }
    }


    public List<InfraAgent> getListAgentsToSchedule() {
        return listAgentsToSchedule;
    }


    @Override
    public void changeSpeed(EnumSpeed speed) {
        switch (speed) {
            case HUNDRED:
                int speed1 = 10;
                break;
            case SIXTY_FIVE:
                speed1 = 15;
                break;
            case FIFTY:
                speed1 = 20;
                break;
            case TWENTY_FIVE:
                speed1 = 50;
                break;
            case TEN:
                speed1 = 100;
                break;
        }
    }

    @Override
    public void stopScheduling() {
    }

    @Override
    public void addSchedulingListener(SchedulerListener schedulerListener) {
        schedulerListeners.add(schedulerListener);
    }

    @Override
    public void addAgent(InfraAgent infraAgent) {
        listAgentsToSchedule.add(infraAgent);
    }

    @Override
    public void removeAgent(InfraAgent infraAgent) {
        listAgentsToSchedule.remove(infraAgent);
    }


    @Override
    public void setMaxCycleAgent(int maxCycleAgent) {
        this.maxCycleAgent = maxCycleAgent;
    }

    @Override
    public void resetCurrentCycleAgent() {
        this.currentAgentCycle = 0;
    }
}
