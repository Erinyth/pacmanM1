package controller;
import model.Game;
import model.PacmanGame;
import vue.View;


public class ControleurGame implements ControleurAvance{

	private PacmanGame etatJeu;
	private View affichagePartie;

	public ControleurGame(PacmanGame gameState) {
		this.etatJeu = gameState;
		affichagePartie = new View(etatJeu, this);
	}
	//Run only one step
	public void step() {
		etatJeu.stop();
		etatJeu.step();
		affichagePartie.disablePause();
		affichagePartie.enableRun();
		affichagePartie.enableInit();
	}

	public void start() {
		etatJeu.launch();

		affichagePartie.disableRun();
		affichagePartie.enablePause();
		affichagePartie.enableStep();
		affichagePartie.enableInit();

	}

	public void init() throws Exception {
		if (etatJeu.getNomLaby() != null)	//On ne fera rien si aucun laby n'a été choisi
		{
			etatJeu.stop();
			etatJeu.init();

			affichagePartie.disableInit();
			affichagePartie.enableRun();
			affichagePartie.enablePause();
			affichagePartie.enableStep();
		}
	}

	public void pause(){
		etatJeu.stop();

		affichagePartie.disablePause();
		affichagePartie.enableRun();
		affichagePartie.enableInit();
		affichagePartie.enableStep();

	}

	public void receptionSlider(int valeurSlider)
	{
		etatJeu.setTPS(valeurSlider);
	}

	public void receptionNouveauLaby(String chemin)
	{
		System.out.println(chemin);
		try {
			etatJeu.setNomLaby(chemin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
