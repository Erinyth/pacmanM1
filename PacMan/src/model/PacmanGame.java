package model;

import java.util.ArrayList;



public class PacmanGame extends Game {

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
			this.notifierObservateur();	
		}
		else
		{
			System.out.println("Caca");
		}
	}

	@Override
	public void takeTurn() {
		System.out.println("TakeTurn Pacman");

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


}
