package comport;

import model.Agent;
import model.AgentAction;
import model.Maze;

public class CompAleatoire implements Comportement
{
	public AgentAction getAction(Agent a, Maze laby) 
	{
		int alea = (int)(Math.random()*4);
		switch (alea)
		{
			case 0:
				AgentAction agA_n = new AgentAction(laby.NORTH);
				return agA_n;
			case 1:
				AgentAction agA_s = new AgentAction(laby.SOUTH);
				return agA_s;
			case 2:
				AgentAction agA_e = new AgentAction(laby.EAST);
				return agA_e;
			case 3:
				AgentAction agA_w = new AgentAction(laby.WEST);
				return agA_w;
			default:
				System.out.println("On va dans le default, c'est pas très très normal");
		}
		return null;
	}
}
