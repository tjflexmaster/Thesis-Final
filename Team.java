package simulator;

public abstract class Team implements ITeam {
	
	protected ArrayList<IEvent> _events = new ArrayList<IEvent>();
	protected ArrayList<IActor> _actors = new ArrayList<IActor>();
	protected ComChannelList _com_channels = new ComChannelList();
	

	@Override
	public HashMap<IActor, ITransition> getActorTransitions() {
		HashMap<IActor, ITransition> result = new HashMap<IActor, ITransition>();
		for( IActor a : _actors ) {
			HashMap<IActor, ITransition> transitions = a.getTransitions();
			if(transitions != null)
				result.putAll(transitions);
		}
		
		return result;
	}
	
	@Override
	public ArrayList<IEvent> getEvents() {
		return _events;
	}
	
	@Override
	public ComChannelList getAllChannels()
	{
		return _com_channels;
	}
	
	public void addActor(IActor actor) 
	{
		assert !_actors.contains(actor):"Actor is already a part of the team";
		
		_actors.add(actor);
	}
	
	public void addEvent(IEvent event, int count) 
	{
		assert !_events.contains(event):"Event is already a part of the team";
		event.setEventCount(count);
		_events.add(event);
	}
	
	public void addComChannel(ComChannel c)
	{
		assert !_com_channels.containsKey(c.name()):"Com channel already defined:" + c.name();
		_com_channels.add(c);
	}

	public ComChannel getComChannel(String name)
	{
		return _com_channels.get(name);
	}
	
	public String getStateName(String actorName){
		String state = null;
		
		for(IActor actor : _actors)
			if(actorName.equals(actor.name()))
				state = actor.getCurrentState().toString();
		
		return state;
	}
}
