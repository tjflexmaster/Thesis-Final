package simulator;

public class State implements IState, IMetrics {
	
	/**
	 * this is the name of the state
	 */
	private String _name;
	private int _load;
	private ArrayList<ITransition> _transitions;

	public State(String name, int load) {
		_name = name;
		_load = Math.max(0, Math.min(4, load)); //Between 0 and 2
		_transitions = new ArrayList<ITransition>();
	}
	
	public State add(ITransition new_transition)
	{
		if(_transitions.contains(new_transition)){
			return this;
		}
		
		//The Actor will decide how to handle the transition and how to sort them.
		_transitions.add(new_transition);
		new_transition.setIndex(_transitions.indexOf(new_transition));
		
		return this;
	}
	
	@Override
	public ArrayList<ITransition> getEnabledTransitions() {
		ArrayList<ITransition> enabled = new ArrayList<ITransition>();
		for (ITransition t : _transitions) {
		  boolean enabledTransition = false;
		  if ( t.isEnabled() ) {
		    enabled.add(t);
		    enabledTransition = true;
		  }
	    if (Simulator.getSim().mode().compareTo(DebugMode.DEBUG_VERBOSE) >= 0) {
	      System.out.println("\t\t\tEnabled: "+enabledTransition+"  Desc:"+t.toString());
	    }
		}
		return enabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if(obj instanceof String && _name.equals(obj))
			return true;
		if (!(obj instanceof State))
			return false;
		State other = (State) obj;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		return true;
	}
	
	@Override
	public String getName()
	{
		return _name;
	}
	
	@Override
	public int getLoad()
	{
	  return _load;
	}

	public String toString() {
		return _name;
	}

  @Override
  public void setMetrics(MetricContainer c)
  {
    c.currentState = getName();
    c.numOfTransitions = _transitions.size();
    
    //Fill State Metrics
    StateMetrics m = new StateMetrics();
    c.currentStateMetrics = m;
    
    m.load = _load;

    //Get all inputs
    ComChannelList inputChannels = new ComChannelList();
    HashMap<String, IComLayer> inputLayers = new HashMap<String, IComLayer>();
    MemoryList inputMemory = new MemoryList();
    for(ITransition t : _transitions) {
      inputChannels.putAll(t.getInputChannels());
      inputLayers.putAll( t.getInputLayers());
      inputMemory.putAll( t.getInputMemory());
    }//end for
    
    
    //Work with unique input list
    ComChannelList uniqueChannels = inputChannels.getUniqueChannels();
    m.activeInputs = uniqueChannels.getActiveChannels();
    m.channelsRead = uniqueChannels.size();
    m.activeChannelsRead = uniqueChannels.countActiveChannels();
    m.audioChannelInputs = uniqueChannels.countActiveChannels(ComChannel.Type.AUDIO);
    m.visualChannelInputs = uniqueChannels.countActiveChannels(ComChannel.Type.VISUAL);
    m.memoryInputs = inputMemory.getUniqueMemory().size();
    if ( uniqueChannels.countChannels(ComChannel.Type.AUDIO) > 0)
      m.channelTypes++;
    if ( uniqueChannels.countChannels(ComChannel.Type.VISUAL) > 0 )
      m.channelTypes++;
    
    //Work with layers
    ArrayList<String> counted_layers = new ArrayList<String>();
    for(Entry<String, IComLayer> e : inputLayers.entrySet() ) {
        String hash = e.getValue() != null ? 
            e.getKey() + "_" + e.getValue().name() :
            e.getKey() + "_null";
        if(!counted_layers.contains(hash)) {
          counted_layers.add(hash);
          m.layersRead++;
        }
    }
    
  }


}
