package comport;

import java.util.concurrent.ThreadLocalRandom;

import model.Agent;
import model.AgentAction;
import model.Maze;


/**
 * Va toujours dans la même direction jusqu'à ce qu'il rencontre un mur, puis modifie aléatoirement sa trajectoire
 * @author etudiant
 *
 */
public class CompChangementMur implements Comportement{

	@Override
	public AgentAction getAction(Agent ag, Maze laby) {

		AgentAction agA = new AgentAction(ag.getPositionAgent().getDir());

		if (laby.deplacementAutorise(ag, agA))
		{
			return agA;
		}
		else
		{
			do
			{
				int rand = (int)(Math.random()*4);
				
				switch (rand)
				{
				case 0:
					agA = new AgentAction(laby.NORTH);
					break;
				case 1:
					agA = new AgentAction(laby.SOUTH);
					break;
				case 2:
					agA = new AgentAction(laby.WEST);
					break;
				case 3:
					agA = new AgentAction(laby.EAST);
					break;
				}
				
				if (laby.deplacementAutorise(ag, agA))
					return agA;
			}
			while (!laby.deplacementAutorise(ag, agA));
			return null;
		}
	}

}
