package model;



public class Agent 
{
	PositionAgent pos;
	
	// 1 représente Pacman, 0 les fantômes
	boolean typeAgent;
	boolean mort;
	Etat etat;
		
	public Agent(int posX, int posY, boolean typeAgent)
	{
		this.pos = new PositionAgent(posX, posY, Maze.NORTH);
		this.typeAgent = typeAgent;
		this.mort = false;
	}
	
	public Agent(PositionAgent posAg, boolean typeAgent)
	{
		this.pos = posAg;
		this.typeAgent = typeAgent;
	}

	public boolean isTypePacman() {
		return typeAgent;
	}

	public void setTypeAgent(boolean typeAgent) {
		this.typeAgent = typeAgent;
	}
	
	public PositionAgent getPositionAgent()
	{
		return pos;
	}

	public void setPos(PositionAgent nPos)
	{
		this.pos = nPos;
	}
	
	public boolean estMort()
	{
		return this.mort;
	}
	
	public void setMort()
	{
		this.mort = true;
	}
	
}
