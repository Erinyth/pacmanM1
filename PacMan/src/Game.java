import java.util.ArrayList;
import java.util.List;


abstract class Game implements Runnable, Observable {
	protected int nbTours;
	protected int nbToursMAX;
	protected int tourParSec = 1000;

	public boolean partieFinie;
	public boolean isRunning = false;

	public Thread threadSimul;

	private List<Observateur> listeObservateurs = new ArrayList<>();

	public Game(int nbMaxTours) {
		nbToursMAX=nbMaxTours;
		nbTours=0;
		nomLabyrinthe = "";

		//temporaire
		partieFinie=false;
	}

	public void setTPS(int i)
	{
		this.tourParSec = (1000)/i;
	}


	//Sera redéfinie en fonction des besoins dans les sous classes
	abstract void intializeGame() throws Exception;
	abstract void takeTurn();
	abstract void gameOver();
	
	//utile seulement pour le jeu PacMan
	public String nomLabyrinthe;
	
	public void setNomLaby(String nouvNom)
	{
		nomLabyrinthe = nouvNom;
	}
	
	/////////////////////////////////////
	
	/**
	 * Initialise le plateau de jeu
	 * @throws Exception 
	 */
	public void init() throws Exception
	{
		System.out.println("Initialisation basique");
		this.nbTours=0;
		intializeGame();	

		partieFinie=false;
	}

	/**
	 * Effectue un seul tour du jeu
	 */
	public void step()
	{		
		if (!partieFinie)
		{
			this.nbTours++;		//PEUT ETRE A DEPLACER PLUS TARD
			takeTurn();

			this.notifierObservateur();
		}
		else
			gameOver();
	}

	public void launch()
	{
		threadSimul = new Thread(this);
		threadSimul.start();
		isRunning = true;
	}

	public void run()
	{		
		System.out.println("C'est parti!");

		while (!partieFinie && isRunning && (nbTours <= nbToursMAX))
		{
			try {
				Thread.sleep((long)tourParSec);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			step();
		}

		if (isRunning)
		{
			partieFinie=true;
			gameOver();
		}
	}

	public void stop()
	{
		isRunning = false;
	}


	/**
	 * Méthodes d'interface
	 */
	public void enregistrerObservateur(Observateur observateur)
	{
		listeObservateurs.add(observateur);
	}

	public void supprimerObservateur(Observateur observateur)
	{
		listeObservateurs.remove(observateur);
	}

	public void notifierObservateur()
	{
		for(int i = 0; i< listeObservateurs.size(); i++) {
			Observateur observateur = listeObservateurs.get(i);
			observateur.actualiser();
		}
	}

}
