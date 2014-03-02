package simulator;

public abstract class Actor implements IActor {
	/**
	 * this variable represents the name we give to the actor
	 */
	private String _name;
	protected State _currentState;
	/**
   * this represents all of the subactors that this actor holds
   */
  private ArrayList<Actor> _subactors = null;
	
	protected ArrayList<IState> _states = new ArrayList<IState>();
	
	/**
	 * This method must be implemented by the Actor.  
	 * @return
	 */
	public HashMap<IActor, ITransition> getTransitions(){
		
		State state = this.getCurrentState();
		
		if (Simulator.getSim().mode().compareTo(DebugMode.DEBUG_VERBOSE) >= 0) {
      System.out.println("\t\tActor("+this.name()+") in State("+state.getName()+
          ") transition summary:");
    }
		
		ArrayList<ITransition> enabledTransitions = state.getEnabledTransitions();
		
		if(enabledTransitions.size() == 0)
			return null;
		ITransition nextTransition = enabledTransitions.get(0);
		for(ITransition t : enabledTransitions){
			if(nextTransition.priority() < t.priority()){
				nextTransition = t;
			}
		}
		HashMap<IActor, ITransition> transitions = new HashMap<IActor, ITransition>();
		transitions.put(this, nextTransition);
		return transitions;
	}
	
	/**
	 * 
	 * @return return the current state of the actor
	 */
	public State getCurrentState() {
	  return _currentState;
	}
	
	public ArrayList<IState> getStates() {
	  return _states;
	}
	
	public String name()
	{
		return _name;
	}
	
	protected ArrayList<Actor> getSubActors()
	{
		return _subactors;
	}
	
	protected void addSubActor(Actor a)
	{
		_subactors.add(a);
	}

	
	
	/**
	 * HELPER METHODS
	 */
	protected void setName(String name){
		_name = name;
	}
	protected void startState(IState state)
	{
		assert _states.contains(state):"Start state not available. Actor:" + 
		    this.name() + " State:" + state.getName();
		_currentState = (State) state;
	}
	
	protected Actor add(IState state)
	{
		if ( _states.contains(state) )
			return this;
		
		_states.add(state);
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		if (name() == null) {
			if (other.name() != null)
				return false;
		} else if (!name().equals(other.name()))
			return false;
		return true;
	}
	

	@Override
	public int hashCode()
	{
		return name().hashCode();
	}
	
	public String toString() {
		return name();
	}
}
