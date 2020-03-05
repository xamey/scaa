package mas.infrastructure.scheduler;

import mas.infrastructure.agent.InfraAgent;

import java.util.List;

public class ClassicStrategy implements ISchedulingStrategies {

    private final List<InfraAgent> listAgentsToSchedule; // list of observed agents
    private final List<SchedulerListener> schedulerListeners; // list of observers
    private int currentAgentCycle;
    private int maxCycleAgent;
    private boolean isRunning;

    /**
     * @param listInfraAgents
     * @param currentSchedulerListeners
     */
    public ClassicStrategy(List<InfraAgent> listInfraAgents, List<SchedulerListener> currentSchedulerListeners) {
        listAgentsToSchedule = listInfraAgents;
        this.currentAgentCycle = 0;
        this.maxCycleAgent = 400;
        this.isRunning = true;
        this.schedulerListeners = currentSchedulerListeners;
        changeSpeed(EnumSpeed.HUNDRED);
    }

    @Override
    public void startScheduling() {
        this.isRunning = true;
        this.currentAgentCycle = 0;

        InfraAgent currentInfraAgent;
        while (isRunning) {
            synchronized (this) {

                while (this.currentAgentCycle < this.maxCycleAgent && listAgentsToSchedule.size() > 0) {
                    currentInfraAgent = listAgentsToSchedule.get(0);
                    currentInfraAgent.run();
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
        int numberCyclesBound = numberCycles * 3;

        InfraAgent currentInfraAgent;

        while (currentCycle < numberCyclesBound && listAgentsToSchedule.size() > 0) {
            currentInfraAgent = listAgentsToSchedule.get(0);

            currentInfraAgent.run(); //Launch the behavior of the agent in its current state
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

    public List<SchedulerListener> getSchedulerListeners() {
        return schedulerListeners;
    }

    @Override
    public void stopScheduling() {
        synchronized (this) {
            this.isRunning = false;
        }
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
        System.out.println(" Deleting from the scheduling strategy the agent = " + infraAgent.toString());
        listAgentsToSchedule.remove(infraAgent);
    }

    @Override
    public void setMaxCycleAgent(int maxCycleAgent) {
        //OCELogger.log(Level.INFO,"Changement du nombre de cycles agent par cycle moteur, nouvelle valeur = "+ maxCycleAgent);
        this.maxCycleAgent = maxCycleAgent;
    }

    @Override
    public void resetCurrentCycleAgent() {
        synchronized (this) {
            this.currentAgentCycle = 0;
        }
    }
}
