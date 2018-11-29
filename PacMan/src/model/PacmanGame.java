package model;

import java.util.ArrayList;
import java.util.Scanner;

import comport.CompAleatoire;
import comport.CompChangementMur;
import comport.Comportement;



public class PacmanGame extends Game {

	//TEMPORAIRE
	Scanner sc = new Scanner(System.in);

	String nomLabyrinthe;
	Maze labyrinthe;
	ArrayList<Agent> listePacmans;
	ArrayList<Agent> listeFantomes;
	Comportement compAgent;

	boolean capsuleActive = false;
	int tourFinCapsule;


	public PacmanGame(int nbMaxTours) throws Exception
	{
		super(nbMaxTours);
		listePacmans = new ArrayList<Agent>();
		listeFantomes = new ArrayList<Agent>();
		compAgent = new CompChangementMur();
	}

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
			Agent pac = new Agent(listePosFan.get(i),false);
			listeFantomes.add(pac);
		}
	}

	public void changerComportementAgent(Comportement c)
	{
		this.compAgent = c;
	}

	public ArrayList<Agent> getListePacman()
	{
		return listePacmans;
	}

	public ArrayList<Agent> getListeFantomes()
	{
		return listeFantomes;
	}

	@Override
	public void intializeGame() throws Exception {
		if (nomLabyrinthe.length() > 0)
		{
			System.out.println("Initialisation du jeu Pacman avec la carte: " + nomLabyrinthe);
			//On recharge le labyrinthe d'origine
			labyrinthe = new Maze(nomLabyrinthe);
			listeFantomes.clear();
			listePacmans.clear();
			compAgent = new CompChangementMur();	//Par défaut
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

		for (int i = 0; i<listeFantomes.size(); i++)
		{			
			AgentAction act = compAgent.getAction(listeFantomes.get(i), labyrinthe);
			deplacerAgent(listeFantomes.get(i),act);
		}


		for (int i = 0; i<listePacmans.size(); i++)
		{
			boolean estMortPacMan = false;

			//Verification mort pacman
			for (int j=0; j<listeFantomes.size(); j++)
			{
				if (!capsuleActive())
				{
					estMortPacMan = mortAgentA(listePacmans.get(i), listeFantomes.get(j));
					if (estMortPacMan)
						listePacmans.remove(i);						
				}
				else
					mortAgentA(listeFantomes.get(j),listePacmans.get(i));
			}
			//System.out.println("Pos PACMAN avant: x=" + listePacmans.get(i).getPositionAgent().getX() + ", y=" + listePacmans.get(i).getPositionAgent().getY());
			//TEMPORAIRE
			/*
			String str = "";
			AgentAction act = new AgentAction(Maze.NORTH);	//Pour initilialiser à quelque chose
			while (!(str.equals("H") || str.equals("G") || str.equals("G") || str.equals("D")))
			{
				System.out.println("Veuillez saisir une direction (H/B/G/D)");
				str = sc.nextLine();

				if (str.equals("H"))
					act = new AgentAction(Maze.NORTH);
				if (str.equals("B"))
					act = new AgentAction(Maze.SOUTH);
				if (str.equals("G"))
					act = new AgentAction(Maze.WEST);
				if (str.equals("D"))
					act = new AgentAction(Maze.EAST);
			}*/

			if (!estMortPacMan)
			{
				AgentAction act = compAgent.getAction(listePacmans.get(i), labyrinthe);
				deplacerAgent(listePacmans.get(i),act);

				//Modification des boules si nécéssaires
				PositionAgent nouvPos = listePacmans.get(i).getPositionAgent();
				if (labyrinthe.isFood(nouvPos.getX(), nouvPos.getY()))
				{
					labyrinthe.setFood(nouvPos.getX(), nouvPos.getY(), false);
				}			

				//Verification que le pacman n'est pas sur une capsule
				if (labyrinthe.isCapsule(nouvPos.getX(), nouvPos.getY()))
				{
					labyrinthe.setCapsule(nouvPos.getX(), nouvPos.getY(), false);
					activationCapsule();
				}		

				//System.out.println("NOTIFICATION OBSERVATEUR");
				
				//Vérification de la mort des fantomes
				for (int j=0; j<listeFantomes.size(); j++)
				{
					if (capsuleActive())
					{
						boolean estMortFantome = mortAgentA(listePacmans.get(i), listeFantomes.get(j));
						if (estMortFantome)
							listeFantomes.remove(j);				
					}
				}
			}
			
			this.notifierObservateur();
			
			//Vérification fin du jeu			
			finJeu();
		}
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
	public boolean deplacerAgent(Agent ag, AgentAction agA)
	{
		if (labyrinthe.deplacementAutorise(ag, agA))
		{
			int nouvX = ag.getPositionAgent().getX() + agA.getVX();
			int nouvY = ag.getPositionAgent().getY() + agA.getVY();

			PositionAgent nouvPos = new PositionAgent(nouvX, nouvY, agA.getDir());
			ag.setPos(nouvPos);

			return true;
		}
		else 
		{
			System.out.println("Déplacement impossible");
			return false;
		}
	}

	public void activationCapsule()
	{	
		capsuleActive = true;
		tourFinCapsule = this.getNbTours() + 20;		
	}

	public boolean capsuleActive()
	{
		if (this.getNbTours() < tourFinCapsule)
			return true;
		else if (this.getNbTours() == tourFinCapsule)
		{
			capsuleActive = false;
			return true;
		}
		else
			return capsuleActive;
	}

	/**
	 * L'agent peut être un pacman ou un fantome suivant l'état de capsule active
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean mortAgentA(Agent a, Agent b)
	{
		System.out.println("Pos A(" +  a.getPositionAgent().getX() + "," + a.getPositionAgent().getY()+"), Type " + a.isTypePacman());
		System.out.println("Pos B(" +  b.getPositionAgent().getX() + "," + b.getPositionAgent().getY()+"), Type " + b.isTypePacman());

		if ((a.typeAgent != b.typeAgent)  && (a.pos.getX() == b.pos.getX()) && (a.pos.getY() == b.pos.getY()))
		{
			System.out.println("MOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOORT");
			//Faire disparaitre agent A
			return true;
		}

		else
			return false;        
	}

	public boolean finJeu()
	{
		//Il existe dans PacMan encore vivants
		if (listePacmans.size() == 0)
			return true;

		//Il existe encore au moins une capsule ou une boule sur le jeu
		for (int i=0; i<labyrinthe.getSizeX(); i++)
			for (int j=0; j<labyrinthe.getSizeY();j++)
			{
				if (labyrinthe.isCapsule(i,j) || labyrinthe.isFood(i,j))
					return false;
				else
					return true;
			}

		return false;
	}
}
