package model;
import controller.ControleurGame;


public class Test {

	public static void main(String[] args) throws Exception {
		
		String cheminInitial = "./layouts/bigCorners.lay";
		PacmanGame jeuPacman = new PacmanGame(500,cheminInitial);
		ControleurGame controleurJeu = new ControleurGame(jeuPacman);
		
		
		
		
		//jeuPacman.run(); On ne démarre pas directement par run, en effet, launch lance le thread, et le thread démarre
		//automatiquement sa méthode run, donc ici, notre jeu
		
	}
	
}
