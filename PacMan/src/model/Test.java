package model;
import controller.ControleurGame;


public class Test {

	public static void main(String[] args) throws Exception {

		PacmanGame jeuPacman = new PacmanGame(500);
		ControleurGame controleurJeu = new ControleurGame(jeuPacman);




		//jeuPacman.run(); On ne démarre pas directement par run, en effet, launch lance le thread, et le thread démarre
		//automatiquement sa méthode run, donc ici, notre jeu

	}

}
