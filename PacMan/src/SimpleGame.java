
public class SimpleGame extends Game {

	public SimpleGame(int nbToursMax)
	{
		super(nbToursMax);
	}
	
	void intializeGame()
	{
		System.out.println("Le jeu s'initialise.");
	}
	
	void takeTurn()
	{
		System.out.println("Le tour " + this.nbTours + " se passe.");
	}
	
	void gameOver()
	{
		System.out.println("Tu es mort Yugi. Même si tu gagnes. Sacrifies toi. Personne ne t'aime. Grosse merde.");
	}
	
}
