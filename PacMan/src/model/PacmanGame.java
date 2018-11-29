package model;

import java.util.ArrayList;
import java.util.Scanner;



public class PacmanGame extends Game {

	//TEMPORAIRE
	Scanner sc = new Scanner(System.in);

	String nomLabyrinthe;
	Maze labyrinthe;
	ArrayList<Agent> listePacmans;
	ArrayList<Agent> listeFantomes;

	/**
	 * Va ajouter autant d'agents que nécessaire dans le jeu
	 */
	public void ajouterAgents()
	{
		int nbPac = labyrinthe.getInitNumberOfPacmans();

		ArrayList<PositionAgent> listePosPac = labyrinthe.getPacman_start();
		for (int i=0; i<nbPac; i++)
		{
			Agent pac = new Agent(listePosPac.get(i),true);
			listePacmans.add(pac);
		}

		int nbFan = labyrinthe.getInitNumberOfGhosts();
		ArrayList<PositionAgent> listePosFan = labyrinthe.getGhosts_start();
		for (int i=0; i<nbFan; i++)
		{
			Agent pac = new Agent(listePosFan.get(i),true);
			listeFantomes.add(pac);
		}
	}
	
	public ArrayList<Agent> getListePacman()
	{
		return listePacmans;
	}
	
	public ArrayList<Agent> getListeFantomes()
	{
		return listeFantomes;
	}

	public PacmanGame(int nbMaxTours) throws Exception
	{
		super(nbMaxTours);
		listePacmans = new ArrayList<Agent>();
		listeFantomes = new ArrayList<Agent>();
	}

	@Override
	public void intializeGame() throws Exception {
		if (nomLabyrinthe.length() > 0)
		{
			System.out.println("Initialisation du jeu Pacman avec la carte: " + nomLabyrinthe);
			//On recharge le labyrinthe d'origine
			labyrinthe = new Maze(nomLabyrinthe);
			ajouterAgents();
		}
		else
		{
			System.out.println("Caca");
		}
	}

	@Override
	public void takeTurn() {
		System.out.println("TakeTurn Pacman");
		AgentAction act;
		for (int i = 0; i<listeFantomes.size(); i++)
		{			
			act = new AgentAction(Maze.WEST);
			deplacerAgent(listeFantomes.get(i),act);
		}
		for (int i = 0; i<listePacmans.size(); i++)
		{
			System.out.println("Pos PACMAN avant: x=" + listePacmans.get(i).getPositionAgent().getX() + ", y=" + listePacmans.get(i).getPositionAgent().getY());
			//TEMPORAIRE
			System.out.println("Veuillez saisir une direction (H/B/G/D)");
			String str = sc.nextLine();
			act = new AgentAction(Maze.NORTH);	//Pour initialiser à quelque chose
			if (str.equals("H"))
				act = new AgentAction(Maze.NORTH);
			if (str.equals("B"))
				act = new AgentAction(Maze.SOUTH);
			if (str.equals("G"))
				act = new AgentAction(Maze.WEST);
			if (str.equals("D"))
				act = new AgentAction(Maze.EAST);
			//
			deplacerAgent(listePacmans.get(i),act);

			PositionAgent nouvPos = listePacmans.get(i).getPositionAgent();
			if (labyrinthe.isFood(nouvPos.getX(), nouvPos.getY()))
			{
				labyrinthe.setFood(nouvPos.getX(), nouvPos.getY(), false);
			}
		}
		
		this.notifierObservateur();
	}
	
	

	@Override
	public void gameOver() {
		System.out.println("GameOver Pacman");

	}

	public String getNomLaby()
	{
		return nomLabyrinthe;
	}

	public void setNomLaby(String nouvNom)
	{
		nomLabyrinthe = nouvNom;
	}

	public Maze getLaby()
	{
		return labyrinthe;
	}


	/******************* Règles ****************************/
	public boolean deplacementAutorise(Agent ag, AgentAction agA)
	{
		int nouvX = ag.getPositionAgent().getX() + agA.getVX();
		int nouvY = ag.getPositionAgent().getY() + agA.getVY();

		if (labyrinthe.isWall(nouvX, nouvY))
			return false;
		else
			return true;
	}

	public void deplacerAgent(Agent ag, AgentAction agA)
	{
		if (deplacementAutorise(ag, agA))
		{
			int nouvX = ag.getPositionAgent().getX() + agA.getVX();
			int nouvY = ag.getPositionAgent().getY() + agA.getVY();

			PositionAgent nouvPos = new PositionAgent(nouvX, nouvY, agA.getDir());
			ag.setPos(nouvPos);
		}
	}

}
