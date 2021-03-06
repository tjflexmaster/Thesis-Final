package simulator;

public abstract class Event implements IEvent, IActor {
	
	private int _count = 0;
	protected String _name;
	protected ITransition _transition;
	protected State _currentState = new State("Event", 0);
	
	public abstract ITransition getEnabledTransition();
	
	public void setEventCount(int count)
	{
		if ( count > 0 )
			_count = count;
		else
			_count = 0;
	}
	
	public void deactivate() 
	{
	  //Clear all outputs
    for(Entry<String, ComChannel> entry : _transition.getOutputChannels().entrySet()) 
    {
      entry.getValue().clearLayers();
    }
	}
	
	public int getEventCount()
	{
		return _count;
	}
	
	public boolean isFinished()
	{
		if ( _count > 0 )
			return false;
		else
			return true;
	}
	
	public void incrementCount()
	{
		_count++;
	}
	
	public void decrementCount()
	{
		_count = Math.max(0, --_count);
	}
	
	public String name()
	{
		return _name;
	}
	
	public HashMap<IActor, ITransition> getTransitions()
	{
		HashMap<IActor, ITransition> result = new HashMap<IActor, ITransition>();
		
		result.put(this, getEnabledTransition());
		return result;
	}
	
	protected State getState()
	{
		return (State) _currentState;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IActor other = (IActor) obj;
		if (_name == null) {
			if (other.name() != null)
				return false;
		} else if (!_name.equals(other.name()))
			return false;
		return true;
	}
	

	@Override
	public int hashCode()
	{
		return _name.hashCode();
	}
}
