package simulator;

public interface IDeltaClock {

	/**
	 * Adds a transition to the delta clock for a specific Actor, the time designates
	 * how long before this transition should occur.  It will automatically be changed
	 * into delta clock time.
	 */
	void addTransition(IActor actor, ITransition transition, int time);
	
	void removeTransition(IActor actor);
	
	int getTimeTillTransition(IActor actor);
	
	ITransition getActorTransition(IActor actor);
	
	/**
	 * Advances time on the delta clock, this means that all transitions with time 0 are removed
	 * and the next set of transitions have their clock time reduced to 0.
	 */
	void advanceTime();
	
	/**
	 * Returns a list of transitions that are ready to be fired
	 */
	HashMap<IActor, ITransition> getReadyTransitions();
	
	int getElapsedTime();
}
