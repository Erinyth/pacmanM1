package model;

import java.util.ArrayList;
import java.util.List;


public abstract class Game implements Runnable, Observable {
	private int nbTours;
	protected int nbToursMAX;
	protected int tourParSec = 1000;

	public boolean partieFinie;
	public boolean isRunning = false;

	public Thread threadSimul;

	private List<Observateur> listeObservateurs = new ArrayList<>();

	public Game(int nbMaxTours) {
		nbToursMAX=nbMaxTours;
		setNbTours(0);

		//temporaire
		partieFinie=false;
	}
	
	public int getNbTours() {
		return nbTours;
	}

	public void setNbTours(int nbTours) {
		this.nbTours = nbTours;
	}

	public void setTPS(int i)
	{
		this.tourParSec = (1000)/i;
	}


	//Sera redéfinie en fonction des besoins dans les sous classes
	public abstract void intializeGame() throws Exception;
	public abstract void takeTurn();
	public abstract void gameOver();
		
	/////////////////////////////////////
	
	/**
	 * Initialise le plateau de jeu
	 * @throws Exception 
	 */
	public void init() throws Exception
	{
		System.out.println("Initialisation basique");
		this.setNbTours(0);
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
			this.setNbTours(this.getNbTours() + 1);		//PEUT ETRE A DEPLACER PLUS TARD
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

		while (!partieFinie && isRunning && (getNbTours() <= nbToursMAX))
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
