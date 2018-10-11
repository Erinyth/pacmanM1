

public class Agent 
{
	PositionAgent pos;
	
	// 1 représente Pacman, 0 les fantômes
	boolean typeAgent; 
	
	/*
	 * 1 pour oui
	 * 0 pour non
	 * variables représentant si l'agent a peut manger l'agent b
	 * ne concerne pas les pacGum non les cerises.
	 */
	//boolean peutManger;
	
	public Agent(int posX, int posY, boolean typeAgent/*, boolean peutManger*/)
	{
		this.pos = new PositionAgent(posX, posY, Maze.NORTH);
		this.typeAgent = typeAgent;
		//this.peutManger = peutManger;
	}
	
	public Agent(PositionAgent posAg, boolean typeAgent/*, boolean peutManger*/)
	{
		this.pos = posAg;
		this.typeAgent = typeAgent;
		//this.peutManger = peutManger;
	}


	public boolean isTypePacman() {
		return typeAgent;
	}

	public void setTypeAgent(boolean typeAgent) {
		this.typeAgent = typeAgent;
	}

	/*public boolean getPeutManger() {
		return peutManger;
	}

	public void setPeutManger(boolean peutManger) {
		this.peutManger = peutManger;
	}*/
}
