package model;


public class SimpleGame extends Game {

	public SimpleGame(int nbToursMax)
	{
		super(nbToursMax);
	}
	
	public void intializeGame()
	{
		System.out.println("Le jeu s'initialise.");
	}
	
	public void takeTurn()
	{
		System.out.println("Le tour " + this.getNbTours() + " se passe.");
	}
	
	public void gameOver()
	{
		System.out.println("Tu es mort Yugi.");
	}
	
}
