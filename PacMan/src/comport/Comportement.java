package comport;

import model.Agent;
import model.AgentAction;
import model.Maze;

public interface Comportement {

	public AgentAction getAction(Agent ag, Maze laby);
	
}
