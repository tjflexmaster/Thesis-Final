package simulator;

public interface IActor extends IMetrics {

	/**
	 * Returns the Actor transition and the transitions of any sub-actors.
	 * Note: An Actor can only take 1 transition.  If more are needed they should be placed 
	 * in sub-actors and their transitions should be passed through this method.
	 */
	HashMap<IActor, ITransition> getTransitions();
	
	String name();

	IState getCurrentState();
	
	Vector<JFreeChart> getCharts();
	
	Vector<MetricDisplayPanel> getPanels();
	
}
