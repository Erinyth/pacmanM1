import java.util.ArrayList;


public class PacmanGame extends Game {

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
		for (int i=0; i<nbPac; i++)
		{
			Agent pac = new Agent(listePosFan.get(i),true);
			listeFantomes.add(pac);
		}
	}
	
	public PacmanGame(int nbMaxTours, String nomLabyrinthe) throws Exception
	{
		super(nbMaxTours);
		labyrinthe = new Maze(nomLabyrinthe);
		listePacmans = new ArrayList<Agent>();
		listeFantomes = new ArrayList<Agent>(); 
		ajouterAgents();
	}

	@Override
	void intializeGame() throws Exception {
		System.out.println("Initialisation du jeu Pacman avec la carte: " + nomLabyrinthe);
		//On recharge le labyrinthe d'origine
		labyrinthe = new Maze(nomLabyrinthe);
		
	}

	@Override
	void takeTurn() {
		System.out.println("TakeTurn Pacman");
		
	}

	@Override
	void gameOver() {
		System.out.println("GameOver Pacman");
		
	}
	
	
	
}
